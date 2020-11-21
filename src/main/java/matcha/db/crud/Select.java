package matcha.db.crud;

import matcha.utils.EventType;

public class Select {

    public static String selectImage = "SELECT id, index, src, profileId, avatar FROM images";
    public static String selectImages = "SELECT * FROM images";
    public static String selectEvents = "SELECT * FROM events";
    public static String selectLocation = "SELECT * FROM locations";
    public static String selectRaiting = "SELECT * FROM rating";
    public static String selectBlacklist = "SELECT * FROM blacklist WHERE fromLogin = :fromLogin AND toLogin = :toLogin";
    public static String selectConnectedElement = "SELECT * FROM connected WHERE (fromLogin = :fromLogin OR fromLogin = :fromLogin2) AND (toLogin = :toLogin OR toLogin = :toLogin2) LIMIT 1";
    public static String selectBlacklists = "SELECT * FROM blacklist";
    public static String selectConnectedList = "SELECT * FROM connected";
    public static String selectConnectedWithUser = "SELECT * FROM connected WHERE (fromLogin = :login OR toLogin = :login)";
    public static String selectImageLikeEvent = "SELECT * FROM imageLikeEvents";
    public static String selectProfile = "SELECT * FROM profiles";
    public static String selectUsers = "SELECT * FROM users";

    public static String selectImagesCountById = "SELECT COUNT(*) FROM images WHERE id = :id";
    public static String selectLocationsCountById = "SELECT COUNT(*) FROM locations WHERE id = ?";
    public static String selectRatingCountById = "SELECT COUNT(*) FROM rating WHERE id = ?";
    public static String selectBlacklistCountById = "SELECT COUNT(*) FROM blacklist WHERE id = ?";
    public static String selectImageLikeEventsCountById = "SELECT COUNT(*) FROM imageLikeEvents WHERE id = ?";
    public static String selectProfilesCountById = "SELECT COUNT(*) FROM profiles WHERE id = :id";
    public static String selectBlackListCount = "SELECT COUNT(*) FROM blacklist WHERE fromLogin = :fromLogin AND toLogin = :toLogin";
    public static String selectUsersCountByLogin = "SELECT COUNT(*) FROM users WHERE login = :login";
    public static String selectUsersCountByActivationCode = "SELECT COUNT(*) FROM users WHERE activationCode = :activationCode";
    public static String selectUsersCountByLoginAndActivationCode = "SELECT COUNT(*) FROM users WHERE login = :login AND activationCode = :activationCode";
    public static String selectUserAndProfileByActivationCode = "SELECT * FROM users u INNER JOIN profiles p ON u.profileId = p.id WHERE activationCode = ?";
    public static String selectNewChatMessages = "SELECT * FROM chat WHERE toLogin = ? AND fromLogin = ? AND read = FALSE";
    public static String selectCountAllNewChatMessages = "SELECT * FROM chat WHERE toLogin = ? AND read = FALSE";
    public static String selectChatMessages = "SELECT * FROM chat WHERE toLogin = ? AND fromLogin = ? ORDER BY time DESC LIMIT ?";
    public static String selectFullChatMessages = "SELECT * FROM chat WHERE (toLogin = ? OR toLogin = ?) AND (fromLogin = ? OR fromLogin = ?) ORDER BY time DESC LIMIT ?";

    public static String selectImageById = "SELECT * FROM images WHERE id = :id LIMIT 1";
    public static String selectImageByProfileId = "SELECT * FROM images WHERE profileId = :profileId LIMIT 5";
    public static String selectLocationById = "SELECT * FROM locations WHERE id = ? LIMIT 1";
    public static String selectLocationByUser = "SELECT * FROM locations WHERE user = ?";
    public static String selectLocationByUserIdAndActive = "SELECT * FROM locations WHERE profileId = :profileId AND active = TRUE ORDER BY time DESC LIMIT 1";
    public static String selectLocations = "SELECT * FROM locations";
    public static String selectRatingById = "SELECT * FROM rating WHERE id = ? LIMIT 1";
    public static String selectBlacklistById = "SELECT * FROM blacklist WHERE id = ? LIMIT 1";
    public static String selectImageLikeEventById = "SELECT * FROM imageLikeEvents WHERE id = ? LIMIT 1";
    public static String selectEventByLogin = "SELECT * FROM events WHERE (type = :type1 OR type = :type2) AND login = :login AND data = :data ORDER BY time DESC LIMIT 1";
    public static String selectEventConnectedOrUnlike = "SELECT * FROM events WHERE (type = :type1 OR type = :type2) AND login = :login AND data = :data ORDER BY time DESC LIMIT 1";

    public static String selectHistoryEvents = "SELECT e.type, e.data as login, e.time, e.active, u.fname, u.lname FROM events e inner join users u " +
            "WHERE e.login = :login AND e.login = u.login AND e.data <> '' AND e.data <> e.login ORDER BY time DESC LIMIT :limit OFFSET :offset";
    public static String selectNotificationEvents = "SELECT e.type, e.login as login, e.time, e.active, u.fname, u.lname FROM events e inner join users u " +
            "WHERE e.data = :data AND e.login = u.login AND e.data <> e.login ORDER BY time DESC LIMIT :limit OFFSET :offset";

//    public static String selectHistoryEvents = "SELECT e.type, e.data as login, e.time, e.active, u.fname, u.lname, i.id as image.id FROM events e inner join users u inner join images i " +
//            "WHERE e.login = :login AND e.login = u.login AND e.data <> '' AND e.data <> e.login AND u.profileId = i.profileId AND i.avatar = TRUE " +
//            "ORDER BY time DESC LIMIT :limit OFFSET :offset";
//    public static String selectNotificationEvents = "SELECT e.type, e.login as login, e.time, e.active, u.fname, u.lname FROM events e inner join users u " +
//            "WHERE e.data = :data AND e.login = u.login AND e.data <> e.login AND u.profileId = i.profileId AND i.avatar = TRUE " +
//            "ORDER BY time DESC LIMIT :limit OFFSET :offset";



public static String selectUserEventsCount = "SELECT COUNT(*) FROM events WHERE login = :login OR data = :login";

    public static String selectActiveLikes = "SELECT * FROM events WHERE (type = :type1 OR type = :type2) AND active = TRUE AND login = :login AND data = :data";

    public static String selectProfileById = "SELECT * FROM profiles WHERE id = :id LIMIT 1";
    public static String selectUserByLogin = "SELECT * FROM users WHERE login = :login LIMIT 1";
    public static String selectUserById = "SELECT * FROM users WHERE id = ? LIMIT 1";
    public static String selectUserByActivationCode = "SELECT * FROM users WHERE activationCode = :activationCode LIMIT 1";
    public static String selectUserProfileIdByLogin = "SELECT profileId FROM users WHERE login = :login LIMIT 1";
}
