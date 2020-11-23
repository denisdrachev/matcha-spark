package matcha.user.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import matcha.blacklist.model.BlackListMessage;
import matcha.blacklist.service.BlackListService;
import matcha.connected.model.ConnectedWithUserInfo;
import matcha.connected.service.ConnectedService;
import matcha.converter.Utils;
import matcha.event.model.Event;
import matcha.event.model.EventWithUserInfo;
import matcha.event.service.EventService;
import matcha.image.service.ImageService;
import matcha.location.model.Location;
import matcha.location.service.LocationService;
import matcha.mail.MailService;
import matcha.profile.model.ProfileEntity;
import matcha.profile.model.UserProfileWithEmail;
import matcha.profile.model.UserProfileWithoutEmail;
import matcha.profile.service.ProfileService;
import matcha.properties.ConfigProperties;
import matcha.response.Response;
import matcha.tag.service.TagService;
import matcha.user.manipulation.UserManipulator;
import matcha.user.model.UserEntity;
import matcha.user.model.UserInfo;
import matcha.user.model.UserRegistry;
import matcha.user.model.UserUpdateEntity;
import matcha.userprofile.model.UserInfoModel;
import matcha.utils.EventType;
import matcha.validator.ValidationMessageService;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static spark.Spark.exception;

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
        exception(Exception.class, (exception, request, response) -> {
            response.body(validationMessageService.prepareErrorMessage(exception.getMessage()).toString());
        });
    }

    private UserManipulator userManipulator = new UserManipulator();
    private LocationService locationService = LocationService.getInstance();
    private ConfigProperties configProperties = new ConfigProperties();
    private final MailService mailService = new MailService();
    private final ProfileService profileService = new ProfileService();
    private EventService eventService = EventService.getInstance();
    private BlackListService blackListService = BlackListService.getInstance();
    private ConnectedService connectedService = ConnectedService.getInstance();
    private ImageService imageService = ImageService.getInstance();
    private TagService tagService = TagService.getInstance();
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

    public Response userLogout(String token) {
        log.info("Request /logout");
        if (token == null || token.isEmpty()) {
            log.info("Token: {} Пользователь не авторизован.", token);
            return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
        }

        UserEntity user = getUserByToken(token);
        userManipulator.userUpdateToken(user);

        Event newEvent = new Event(EventType.LOGOUT, user.getLogin(), false, "");
        eventService.saveNewEvent(newEvent);

        return validationMessageService.prepareMessageOkOnlyType();
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

        Event newEventLoad = new Event(EventType.PROFILE_LOAD, userByToken.getLogin(), true, login);
        eventService.saveNewEvent(newEventLoad);

        BlackListMessage blackListMessage = blackListService.getBlackListMessage(user.getLogin(), login);
        Event newEventLoaded = new Event(EventType.PROFILE_LOADED, userByToken.getLogin(), !blackListMessage.isBlocked(), login, !blackListMessage.isBlocked());
        eventService.saveNewEvent(newEventLoaded);

        Integer userRating = eventService.getUserRatingByLogin(user.getLogin());
        List<String> userTags = tagService.getUserTags(user.getLogin());

        if (login == null)
            return new UserProfileWithEmail(user, profileById, blackList.isBlocked(), userRating, userTags);

        boolean likeEventFrom = eventService.isLikeEvent(userByToken.getLogin(), user.getLogin());
        boolean likeEventTo = eventService.isLikeEvent(user.getLogin(), userByToken.getLogin());


        return new UserProfileWithoutEmail(user, profileById, blackList.isBlocked(), likeEventFrom, likeEventTo, userRating, userTags);
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

        tagService.saveTags(userInfo.getLogin(), String.join(",", userInfo.getTags()));
        profileService.updateProfile(currentUser.getProfileId(), newProfile);

        Event newEvent = new Event(EventType.PROFILE_UPDATE, userInfo.getLogin(), true, "");
        eventService.saveNewEvent(newEvent);
    }


    public Response profileUpdate(String token, String body) {

        log.info("Request /profile-update body: {}", body);

        if (token == null || token.isEmpty()) {
            log.info("Token: {} Пользователь не авторизован.", token);
            return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
        }

        UserInfoModel userProfile = new Gson().fromJson(body, UserInfoModel.class);

        log.info("Request update user profile:{}", userProfile);

        Response response = validationMessageService.validateMessage(userProfile);
        if (response != null) {
            return response;
        }

        userService.checkUserByLoginAndActivationCode(userProfile.getLogin(), token);
        imageService.checkImagesIsCorrect(userProfile.getImages());
        userService.saveUserInfo(userProfile);
        return validationMessageService.prepareMessageOkOnlyType();
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
        Event newEvent = new Event(eventType, message.getFromLogin(), false, message.getToLogin(), false);
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

    @SneakyThrows
    public Response getNotifications(String token, String limitParam, String offsetParam) {

        log.info("Request /notification?limit={}?offset={}", limitParam, offsetParam);

        int limit;
        int offset;

        try {
            limit = Integer.parseInt(limitParam);
            offset = Integer.parseInt(offsetParam);
            if (limit <= 0 || limit > 50 || offset < 0)
                return validationMessageService.prepareErrorMessage("Некорректные параметры запроса.");
        } catch (Exception e) {
            return validationMessageService.prepareErrorMessage("Некорректные параметры запроса.");
        }

        if (token == null || token.isEmpty()) {
            log.info("Token: {} Пользователь не авторизован.", token);
            return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
        }

        UserEntity userByToken = getUserByToken(token);
        List<EventWithUserInfo> notifications = eventService.getNotifications(userByToken.getLogin(), limit, offset);
        return validationMessageService.prepareMessageOkData(gson.toJsonTree(notifications));
    }

    public Response getHistory(String token, String limitParam, String offsetParam) {

        log.info("Request /history?limit={}?offset={}", limitParam, offsetParam);

        int limit;
        int offset;

        try {
            limit = Integer.parseInt(limitParam);
            offset = Integer.parseInt(offsetParam);
            if (limit <= 0 || limit > 50 || offset < 0)
                return validationMessageService.prepareErrorMessage("Некорректные параметры запроса.");
        } catch (Exception e) {
            return validationMessageService.prepareErrorMessage("Некорректные параметры запроса.");
        }


        if (token == null || token.isEmpty()) {
            log.info("Token: {} Пользователь не авторизован.", token);
            return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
        }

        log.info("Request get history by token: {}", token);

        UserEntity userByToken = userService.getUserByToken(token);
        List<EventWithUserInfo> history = eventService.getHistory(userByToken.getLogin(), limit, offset);

        return validationMessageService.prepareMessageOkData(gson.toJsonTree(history));
    }

    public Response likeUser(String token, String body) {

        log.info("Request /like-user body: {}", body);
        Integer value;
        String loginParam;

        if (token == null || token.isEmpty()) {
            log.info("Token: {} Пользователь не авторизован.", token);
            return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
        }

        try {
            Type empMapType = new TypeToken<Map<String, String>>() {
            }.getType();
            Map<String, String> map = gson.fromJson(body, empMapType);
            value = Integer.parseInt(map.get("value"));
            loginParam = map.get("login");

            if (loginParam == null || loginParam.isEmpty() || value < 0 || value > 1) {
                return validationMessageService.prepareErrorMessage("Некорректные параметры запроса.");
            }
        } catch (Exception e) {
            return validationMessageService.prepareErrorMessage("Некорректные параметры запроса.");
        }

        UserEntity userByToken = getUserByToken(token);

        if (!userManipulator.isUserExistByLogin(loginParam)) {
            return validationMessageService.prepareErrorMessage("Указанный пользователь не найден.");
        }

        if (userByToken.getLogin().equals(loginParam)) {
            return validationMessageService.prepareErrorMessage("Себя лайкнут нельзя :)");
        }

        eventService.setLikeOrUnlike(userByToken.getLogin(), loginParam, value);

        return validationMessageService.prepareMessageOkOnlyType();
    }

    public Response getUnreadUserEvents(String token) {
        log.info("Request /new-notifications");

        if (token == null || token.isEmpty()) {
            log.info("Token: {} Пользователь не авторизован.", token);
            return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
        }

        UserEntity userByToken = getUserByToken(token);
        Integer unreadEventsCount = eventService.getUnreadUserActivityByLogin(userByToken.getLogin());

        return validationMessageService.prepareMessageOkData(unreadEventsCount);
    }

    public Object fakeUserMessage(String token, String body) {
        log.info("Request /fake-user body: {}", body);

        if (token == null || token.isEmpty()) {
            log.info("Token: {} Пользователь не авторизован.", token);
            return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
        }


        String fakeLogin;

        try {
            Map<String, String> map = gson.fromJson(body, Map.class);
            fakeLogin = map.get("login");
            if (fakeLogin == null || fakeLogin.isEmpty()) {
                return validationMessageService.prepareErrorMessage("Некорректные параметры запроса.");
            }
        } catch (Exception e) {
            return validationMessageService.prepareErrorMessage("Некорректные параметры запроса.");
        }

        UserEntity userByToken = getUserByToken(token);

        if (!userManipulator.isUserExistByLogin(fakeLogin)) {
            return validationMessageService.prepareErrorMessage("Указанный пользователь не найден.");
        }

        if (userByToken.getLogin().equals(fakeLogin)) {
            return validationMessageService.prepareErrorMessage("Перестаньте, вы не фейк!");
        }

        Event event = new Event(EventType.FAKE_USER, userByToken.getLogin(), false, fakeLogin);
        eventService.saveNewEvent(event);
        return validationMessageService.prepareMessageOkOnlyType();
    }

    public Response getTestExecute() {
        log.info("Request /test");
        tagService.getUsersWithCommonTags(List.of(1, 2));
        return validationMessageService.prepareMessageOkOnlyType();
    }
}
