package matcha.user.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import matcha.blacklist.model.BlackListMessage;
import matcha.blacklist.service.BlackListService;
import matcha.chat.model.ChatAllNewMessage;
import matcha.chat.model.ChatMessageFull;
import matcha.chat.model.ChatMessageSave;
import matcha.chat.model.ChatNewMessageFromUser;
import matcha.chat.service.ChatService;
import matcha.connected.model.ConnectedWithUserInfo;
import matcha.connected.service.ConnectedService;
import matcha.event.model.Event;
import matcha.event.model.EventWithUserInfo;
import matcha.event.service.EventService;
import matcha.exception.context.IncorrectInputParamsException;
import matcha.exception.db.SelectDBException;
import matcha.exception.user.ProfileNotFilledException;
import matcha.image.service.ImageService;
import matcha.location.model.Location;
import matcha.location.service.LocationService;
import matcha.mail.MailService;
import matcha.model.SearchModel;
import matcha.profile.model.ProfileEntity;
import matcha.profile.model.UserProfileWithEmail;
import matcha.profile.model.UserProfileWithoutEmail;
import matcha.profile.service.ProfileService;
import matcha.properties.ConfigProperties;
import matcha.rating.model.Rating;
import matcha.rating.service.RatingService;
import matcha.response.Response;
import matcha.tag.model.Tag;
import matcha.tag.service.TagService;
import matcha.user.manipulation.UserManipulator;
import matcha.user.model.*;
import matcha.userprofile.model.UserInfoModel;
import matcha.utils.EventType;
import matcha.validator.ValidationMessageService;
import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static matcha.converter.Utils.initRegistryUser;
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
    private ChatService chatService = ChatService.getInstance();
    private RatingService ratingService = RatingService.getInstance();
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

        userEntity.setActive(ConfigProperties.usersDefaultActive);
        userEntity.setProfileId(newProfileId);
        initRegistryUser(userEntity);
        userManipulator.userRegistry(userEntity);

        mailService.sendRegistrationMail(userEntity.getEmail(), userEntity.getActivationCode());

        ratingService.createRating(userRegistry.getLogin());

        Event newEvent = new Event(EventType.REGISTRATION, userRegistry.getLogin(), false, "");
        eventService.saveNewEvent(newEvent);

        userEntity.getLocation().setProfileId(newProfileId);
        locationService.saveLocation(userEntity.getLocation());
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

        UserEntity user = checkUserByToken(token);
        userManipulator.updateUserToken(user);

        Event newEvent = new Event(EventType.LOGOUT, user.getLogin(), false, "");
        eventService.saveNewEvent(newEvent);

        return validationMessageService.prepareMessageOkOnlyType();
    }

    public UserEntity checkUserByToken(String token) {
        UserEntity user = userManipulator.checkUserByToken(token);
        ProfileEntity profileById = profileService.getProfileById(user.getProfileId());
        if (!profileById.isFilled()) {
            throw new ProfileNotFilledException();
        }
        return user;
    }

    public void checkUserExistsByLogin(String token) {
        if (!userManipulator.isUserExistByLogin(token))
            throw new IncorrectInputParamsException();
    }

    public UserEntity getUserByLogin(String login) {
        UserEntity userByLogin = userManipulator.getUserByLogin(login);
        //TODO тут исправил, проверить
        Location locationByLogin = locationService.getLocationByProfileId(userByLogin.getProfileId());
        userByLogin.setLocation(locationByLogin);
        return userByLogin;
    }

    public UserEntity getSimpleUserByLogin(String login) {
        try {
            return userManipulator.getUserByLogin(login);
        } catch (Exception e) {
            throw new SelectDBException("Пользователь не найден");
        }
    }

    public UserEntity getUserByToken(String token) {
        return userManipulator.getUserByToken(token);
    }

    public void saveUser(UserUpdateEntity user) {
        userManipulator.userUpdate(user);
    }

    public boolean activationUserByToken(String token) {
        return userManipulator.activationUserByToken(token);
    }

    public Object getUserProfile(String token, String login) {
        UserEntity userByToken = getUserByToken(token);
        userService.updateTimeByLogin(userByToken.getLogin());

        UserEntity user = getUserByLogin(login != null ? login : userByToken.getLogin());
        Location activeUserLocation = locationService.getLocationIfActiveByProfileId(user.getProfileId());
        user.setLocation(activeUserLocation);
        ProfileEntity profileById = profileService.getProfileByIdWithImagesNotEmpty(user.getProfileId());
        BlackListMessage blackList = blackListService.getBlackListMessage(userByToken.getLogin(), user.getLogin());

        if (login != null) {
            Event newEventLoad = new Event(EventType.PROFILE_LOAD, userByToken.getLogin(), true, login);
            eventService.saveNewEvent(newEventLoad);
        }

        Rating rating = ratingService.getRatingByLogin(user.getLogin());
        List<String> userTags = tagService.getUserTags(user.getLogin());

        if (login == null)
            return new UserProfileWithEmail(user, profileById, blackList.isBlocked(), rating.getRating(), userTags);

        boolean likeEventFrom = eventService.isLikeEvent(userByToken.getLogin(), user.getLogin());
        boolean likeEventTo = eventService.isLikeEvent(user.getLogin(), userByToken.getLogin());


        return new UserProfileWithoutEmail(user, profileById, blackList.isBlocked(), likeEventFrom, likeEventTo, rating.getRating(), userTags);
    }

    public void saveUserInfo(UserInfoModel userInfo, UserEntity currentUser) {
        saveUser(new UserUpdateEntity(userInfo, currentUser.getLogin()));
        if (userInfo.getLocation() != null) {
            Location location = new Location(userInfo.getLocation());
            locationService.initLocationAndUpdate(location, currentUser.getProfileId(), true, true);
        }
        ProfileEntity newProfile = new ProfileEntity(currentUser.getProfileId(), userInfo);
        newProfile.setFilled(true);

        tagService.saveTags(currentUser.getLogin(), userInfo.getTags());
        profileService.updateProfile(currentUser.getProfileId(), newProfile);

        Event newEvent = new Event(EventType.PROFILE_UPDATE, currentUser.getLogin(), true, "");
        eventService.saveNewEvent(newEvent);
    }


    public Response profileUpdate(String token, String body) {
        log.info("Request /profile-update {}", body);

        if (token == null || token.isEmpty()) {
            log.info("Token: {} Пользователь не авторизован.", token);
            return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
        }
        UserInfoModel userProfile = new Gson().fromJson(body, UserInfoModel.class);
        Response response = validationMessageService.validateMessage(userProfile);
        if (response != null) {
            return response;
        }
        UserEntity user = getUserByToken(token);
        imageService.checkImagesIsCorrect(userProfile.getImages());
        if (userProfile.getTags() != null) {
            for (String tag : userProfile.getTags()) {
                if (tag.length() > 10) {
                    return validationMessageService.prepareErrorMessage("Слишкой длинные теги");
                }
            }
        }
        saveUserInfo(userProfile, user);
        return validationMessageService.prepareMessageOkOnlyType();
    }

    public void checkUserByLoginAndActivationCode(String login, String token) {
        userManipulator.checkUserByLoginAndActivationCode(login, token);
    }

    public Response saveBlackList(String token, BlackListMessage message) {
        UserEntity userByToken = checkUserByToken(token);
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

        if (eventService.isLikeEvent(userByToken.getLogin(), message.getToLogin())) {
            eventService.setLikeOrUnlike(userByToken.getLogin(), message.getToLogin(), 0);
        }
        return validationMessageService.prepareMessageOkOnlyType();
    }

    public List<UserEntity> getAllUsers() {
        return userManipulator.getAllUsers();
    }

    public List<UserSearchEntity> getUsersWithFilters(SearchModel searchModel) {
        return userManipulator.getUsersWithFilters(searchModel);
    }

    public Object getUserConnected(String token) {
        log.info("Request /connected");

        if (token == null || token.isEmpty()) {
            log.info("Token: {} Пользователь не авторизован.", token);
            return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
        }
        UserEntity userByToken = checkUserByToken(token);
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
                return validationMessageService.prepareErrorMessage("Некорректные параметры запроса...");
        } catch (Exception e) {
            return validationMessageService.prepareErrorMessage("Некорректные параметры запроса....");
        }

        if (token == null || token.isEmpty()) {
            log.info("Token: {} Пользователь не авторизован.", token);
            return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
        }

        UserEntity userByToken = checkUserByToken(token);
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
                return validationMessageService.prepareErrorMessage("Некорректные параметры запроса.....");
        } catch (Exception e) {
            return validationMessageService.prepareErrorMessage("Некорректные параметры запроса......");
        }
        if (token == null || token.isEmpty()) {
            log.info("Token: {} Пользователь не авторизован.", token);
            return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
        }
        log.info("Request get history by token: {}", token);
        UserEntity userByToken = userService.checkUserByToken(token);
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
                return validationMessageService.prepareErrorMessage("Некорректные параметры запроса.......");
            }
        } catch (Exception e) {
            return validationMessageService.prepareErrorMessage("Некорректные параметры запроса........");
        }

        UserEntity userByToken = checkUserByToken(token);
        ProfileEntity profileById = profileService.getProfileByIdWithImages(userByToken.getProfileId());

        if (profileById.getImages().stream().anyMatch(image -> image.isAvatar() && image.getSrc().isEmpty())) {
            return validationMessageService.prepareErrorMessage("Вы не можете лайкать. У вас не установлен аватар.");
        }

        if (!userManipulator.isUserExistByLogin(loginParam)) {
            return validationMessageService.prepareErrorMessage("Указанный пользователь не найден.");
        }

        if (userByToken.getLogin().equals(loginParam)) {
            return validationMessageService.prepareErrorMessage("Себя лайкнут нельзя :)");
        }
        BlackListMessage blackListMessage = blackListService.getBlackListMessage(userByToken.getLogin(), loginParam);
        if (blackListMessage.isBlocked()) {
            return validationMessageService.prepareErrorMessage("Невозможно лайкнуть пользователя, он заблокирован");
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
        UserEntity userByToken = checkUserByToken(token);
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
                return validationMessageService.prepareErrorMessage("Некорректные параметры запроса.........");
            }
        } catch (Exception e) {
            return validationMessageService.prepareErrorMessage("Некорректные параметры запроса..........");
        }
        UserEntity userByToken = checkUserByToken(token);

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

    public Response getUsers(String token, String body) {
        log.info("Request /get-users?{}", body);

        if (token == null || token.isEmpty()) {
            log.info("Token: {} Пользователь не авторизован.", token);
            return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
        }

        UserEntity user = userManipulator.checkUserByToken(token);
        ProfileEntity profile = profileService.getProfileById(user.getProfileId());
        Location location = locationService.getLocationByProfileId(profile.getId());

        try {
            String[] split = body == null ? ArrayUtils.toArray() : body.split("&");
            Map<String, String> params = new HashMap<>();

            LinkedList<String> sortOrderList = new LinkedList<>();

            for (int i = 0; i < split.length; i++) {
                String[] split1 = split[i].split("=");
                if (split1[0].equals("sortAge")) {
                    sortOrderList.add("sortAge");
                } else if (split1[0].equals("sortLocation")) {
                    sortOrderList.add("sortLocation");
                } else if (split1[0].equals("sortRating")) {
                    sortOrderList.add("sortRating");
                } else if (split1[0].equals("sortTags")) {
                    sortOrderList.add("sortTags");
                }
                params.put(split1[0], split1[1]);
            }

            List<Integer> tagsIds = tagService.getTagsIds(params.get("tags"));
            if (params.get("tags") != null && !params.get("tags").isEmpty() && tagsIds.size() == 0)
                return validationMessageService.prepareMessageOkData(new JSONArray());
            Integer preference;
            if (params.get("needPreference") != null
                    && Integer.parseInt(params.get("needPreference")) == 1
                    && profile.getPreference() != null) {
                preference = profile.getPreference();
            } else {
                preference = null;
            }

            SearchModel searchModel = new SearchModel(location, tagsIds, user.getLogin(), preference, params, sortOrderList);

            Response response = validationMessageService.validateMessage(searchModel);
            if (response != null) {
                return response;
            }
            return validationMessageService.prepareMessageOkData(gson.toJsonTree(
                    userService.getUsersWithFilters(searchModel)
            ));
        } catch (Exception e) {
            return validationMessageService.prepareErrorMessage("Некорректные параметры запроса............");
        }
    }

    public Response getFullMessagesByLimit(String token, String toLogin, String limit) {
        log.info("Request chat/full toLogin: {} limit: {}", toLogin, limit);

        ChatMessageFull chatMessageFull = new ChatMessageFull(toLogin, limit);
        Response response = validationMessageService.validateMessage(chatMessageFull);
        if (response != null) {
            return response;
        }
        UserEntity user = userService.checkUserByToken(token);
        chatMessageFull.setFromLogin(user.getLogin());
        userService.checkUserExistsByLogin(chatMessageFull.getToLogin());
        return chatService.getFullMessages(chatMessageFull, user.getLogin());
    }

    public Response getNewMessages(String token, String body) {
        log.info("Request /chat/new {}", body);
        ChatNewMessageFromUser chatNewMessageFromUser = new Gson().fromJson(body, ChatNewMessageFromUser.class);
        Response response = validationMessageService.validateMessage(chatNewMessageFromUser);
        if (response != null) {
            return response;
        }
        UserEntity user = userService.checkUserByToken(token);
        chatNewMessageFromUser.setToLogin(user.getLogin());
        userService.checkUserExistsByLogin(chatNewMessageFromUser.getToLogin());
        return chatService.getNewMessages(chatNewMessageFromUser);
    }

    public Response getAllNewMessages(String token, String body) {
        log.info("Request /chat/allnew {}", body);
        ChatAllNewMessage chatNewMessageFromUser = new Gson().fromJson(body, ChatAllNewMessage.class);
        Response response = validationMessageService.validateMessage(chatNewMessageFromUser);
        if (response != null) {
            return response;
        }
        UserEntity user = userService.checkUserByToken(token);
        chatNewMessageFromUser.setFromLogin(user.getLogin());
        userService.checkUserExistsByLogin(chatNewMessageFromUser.getToLogin());
        return chatService.getAllNewMessages(chatNewMessageFromUser);
    }

    public Response postChatMessage(String token, String body) {
        log.info("Request save chat message: {}", body);
        ChatMessageSave chatMessageSave = new Gson().fromJson(body, ChatMessageSave.class);
        Response response = validationMessageService.validateMessage(chatMessageSave);
        if (response != null) {
            return response;
        }
        UserEntity user = userService.checkUserByToken(token);
        chatMessageSave.setFromLogin(user.getLogin());
        userService.checkUserExistsByLogin(chatMessageSave.getToLogin());
        return chatService.saveMessage(chatMessageSave);
    }

    public Response resetPasswordEmail(String body) {
        log.info("Request /reset-password {}", body);
        Map<String, String> map = gson.fromJson(body, HashMap.class);
        UserEntity user = userService.getSimpleUserByLogin(map.get("login"));
        if (user.getEmail().equals(map.get("email")) && user.isActive()) {
            user.setActive(false);
            userManipulator.updateUserToken(user);
            mailService.sendResetPasswordEmail(user.getEmail(), user.getActivationCode());
        } else {
            return validationMessageService.prepareErrorMessage("Указанный пользователь не найден.");
        }
        return validationMessageService.prepareMessageOkOnlyType();
    }

    public Response resetPassword(String token, String body) {
        log.info("Request /change-reset-password {} {}", token, body);
        Map<String, String> map = gson.fromJson(body, HashMap.class);

        String password = map.get("password");
        if (password == null || password.isEmpty())
            return validationMessageService.prepareErrorMessage("Указан недопустимый пароль");

        UserEntity user = userService.checkUserByToken(token);
        user.setPassword(password);
        initRegistryUser(user);
        user.setActive(true);
        userManipulator.updateUserPasswordById(user);
        return validationMessageService.prepareMessageOkOnlyType();
    }

    public void updateTimeByLogin(String userLogin) {
        userManipulator.updateTimeByLogin(userLogin);
    }

    public Response getTags(String token) {
        log.info("Request /get-tags");
        userService.getUserByToken(token);
        List<Tag> popularTags = tagService.getPopularTags(10);
        return validationMessageService.prepareMessageOkData(gson.toJsonTree(popularTags));
    }
}
