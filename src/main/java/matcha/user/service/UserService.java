package matcha.user.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import matcha.blacklist.model.BlackListMessage;
import matcha.blacklist.service.BlackListService;
import matcha.connected.model.ConnectedWithUserInfo;
import matcha.connected.service.ConnectedService;
import matcha.converter.Utils;
import matcha.event.model.Event;
import matcha.event.service.EventService;
import matcha.location.model.Location;
import matcha.location.service.LocationService;
import matcha.mail.MailService;
import matcha.profile.model.ProfileEntity;
import matcha.profile.model.UserProfileWithEmail;
import matcha.profile.model.UserProfileWithoutEmail;
import matcha.profile.service.ProfileService;
import matcha.properties.ConfigProperties;
import matcha.response.Response;
import matcha.user.manipulation.UserManipulator;
import matcha.user.model.UserEntity;
import matcha.user.model.UserInfo;
import matcha.user.model.UserRegistry;
import matcha.user.model.UserUpdateEntity;
import matcha.userprofile.model.UserInfoModel;
import matcha.utils.EventType;
import matcha.validator.ValidationMessageService;

import java.util.List;

@Slf4j
public class UserService implements UserInterface {

    private static UserService userService;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .serializeNulls()
            .create();

    public void init() {
//        registration();
    }

    private UserManipulator userManipulator = new UserManipulator();
    private LocationService locationService = LocationService.getInstance();
    private ConfigProperties configProperties = new ConfigProperties();
    private final MailService mailService = new MailService();
    private final ProfileService profileService = new ProfileService();
    private EventService eventService = EventService.getInstance();
    private BlackListService blackListService = BlackListService.getInstance();
    private ConnectedService connectedService = ConnectedService.getInstance();
    private ValidationMessageService validationMessageService = ValidationMessageService.getInstance();

    public void userRegistration(UserRegistry userRegistry) {

        userManipulator.checkUserExistByLogin(userRegistry.getLogin());

        Integer newProfileId = profileService.createNewProfile();

        UserEntity userEntity = new UserEntity(userRegistry);
        userEntity.setActive(configProperties.isUsersDefaultActive());
        userEntity.setProfileId(newProfileId);
        Utils.initRegistryUser(userEntity);
        userManipulator.userRegistry(userEntity);

        //TODO рефактор отправки EMAIL
//        mailService.sendRegistrationMail(userEntity.getEmail(), userEntity.getActivationCode());

        Event newEvent = new Event(EventType.REGISTRATION, userRegistry.getLogin(), false, "");
        eventService.saveNewEvent(newEvent);
        userRegistry.getLocation().setProfileId(newProfileId);
        locationService.saveLocation(userRegistry.getLocation());
    }

    public Response userLogin(UserInfo user) {
        Response response = userManipulator.userLogin(user);
        Event newEvent = new Event(EventType.LOGIN, user.getLogin(), false, "");
        eventService.saveNewEvent(newEvent);
        return response;
    }

    public void checkUserToToken(String token) {
        userManipulator.checkUserByToken(token);
    }

    public UserEntity getUserByLogin(String login) {
        UserEntity userByLogin = userManipulator.getUserByLogin(login);
        Location locationByLogin = locationService.getLocationByUserId(userByLogin.getId());
        userByLogin.setLocation(locationByLogin);
        return userByLogin;
    }

    public UserEntity getUserByToken(String token) {
        return userManipulator.getUserByToken(token);
    }

    public void saveUser(UserUpdateEntity user, int profileId) {
        //TODO ВМЕСТО ЛОГИНА НУЖНО ПРОФИЛЬ_ИД кидать
        locationService.deactivationLocationByLogin(profileId);
        userManipulator.userUpdate(user);
    }

    public boolean activationUserByToken(String token) {
        return userManipulator.activationUserByToken(token);
    }

    public Object getUserProfile(String token, String login) {

        checkUserToToken(token);
        UserEntity userByToken = getUserByToken(token);

        UserEntity user = getUserByLogin(login != null ? login : userByToken.getLogin());
        Location activeUserLocation = locationService.getLocationByUserId(user.getId());
        user.setLocation(activeUserLocation);
        ProfileEntity profileById = profileService.getProfileByIdWithImagesNotEmpty(user.getProfileId());
        BlackListMessage blackList = blackListService.getBlackListMessage(userByToken.getLogin(), user.getLogin());

        Event newEvent = new Event(EventType.PROFILE_LOAD, userByToken.getLogin(), false, login);
        eventService.saveNewEvent(newEvent);

        Integer userRating = eventService.getUserRatingByLogin(user.getLogin());

        if (login == null)
            return new UserProfileWithEmail(user, profileById, blackList.isBlocked(), userRating);

        boolean likeEventFrom = eventService.isLikeEvent(userByToken.getLogin(), user.getLogin());
        boolean likeEventTo = eventService.isLikeEvent(user.getLogin(), userByToken.getLogin());

        return new UserProfileWithoutEmail(user, profileById, blackList.isBlocked(), likeEventFrom, likeEventTo, userRating);
    }

    //TODO рефакторинг
    public void saveUserInfo(UserInfoModel userInfo) {

        UserEntity currentUser = getUserByLogin(userInfo.getLogin());

        saveUser(new UserUpdateEntity(userInfo), currentUser.getProfileId());

        if (userInfo.getLocation() != null) {
            userInfo.getLocation().setProfileId(currentUser.getId());
            userInfo.getLocation().setActive(true);
            locationService.saveLocation(userInfo.getLocation());
        }

//        Integer userProfileId = userManipulator.getUserProfileId(userInfo.getLogin());
        ProfileEntity newProfile = new ProfileEntity(currentUser.getProfileId(), userInfo);
        newProfile.setFilled(true);

        profileService.updateProfile(currentUser.getProfileId(), newProfile);

        Event newEvent = new Event(EventType.PROFILE_UPDATE, userInfo.getLogin(), false, "");
        eventService.saveNewEvent(newEvent);
    }

    public void checkUserByLoginAndActivationCode(String login, String token) {
        userManipulator.checkUserByLoginAndActivationCode(login, token);
    }

    public Response saveBlackList(String token, BlackListMessage message) {

        checkUserToToken(token);
        UserEntity userByToken = getUserByToken(token);
        //TODO нужна ли строчка снизу???
        getUserByLogin(message.getToLogin());

        message.setFromLogin(userByToken.getLogin());

        if (blackListService.isBlackListExists(message.getFromLogin(), message.getToLogin())) {
            blackListService.updateBlackListMessage(message);
        } else {
            blackListService.insertBlackListMessage(message);
        }

        String eventType;
        if (message.isBlocked()) {
            eventType = EventType.USER_BLOCK;
        } else {
            eventType = EventType.USER_UNBLOCK;
        }
        Event newEvent = new Event(eventType, message.getFromLogin(), true, message.getToLogin());
        eventService.saveNewEvent(newEvent);

        return validationMessageService.prepareMessageOkOnlyType();
    }

    public List<UserEntity> getAllUsers() {
        return userManipulator.getAllUsers();
    }

    public Object getUserConnected(String token) {
        log.info("Request get user connecteds...");

        if (token == null || token.isEmpty()) {
            log.info("Token: {} Пользователь не авторизован.", token);
            return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
        }
        UserEntity userByToken = getUserByToken(token);
        List<ConnectedWithUserInfo> allConnectedWithUser = connectedService.getAllConnectedWithUser(userByToken.getLogin());
        return validationMessageService.prepareMessageOkData(gson.toJsonTree(allConnectedWithUser));
    }
}
