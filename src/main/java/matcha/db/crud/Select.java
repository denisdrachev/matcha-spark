package matcha.db.crud;

public class Select {

    public static String selectImage = "SELECT id, index, src, profileId, avatar FROM images";
    public static String selectImages = "SELECT * FROM images";
    public static String selectEvents = "SELECT * FROM events";
    public static String selectLocation = "SELECT * FROM locations";
    public static String selectRaiting = "SELECT * FROM rating";
    public static String selectBlacklist = "SELECT * FROM blacklist WHERE fromLogin = :fromLogin AND toLogin = :toLogin";
    public static String selectRatingByLogin = "SELECT * FROM rating WHERE login = :login";
    public static String selectConnectedElement = "SELECT * FROM connected WHERE (fromLogin = :fromLogin OR fromLogin = :fromLogin2) AND (toLogin = :toLogin OR toLogin = :toLogin2) LIMIT 1";
    public static String selectBlacklists = "SELECT * FROM blacklist";
    public static String selectRating = "SELECT * FROM rating";
    public static String selectConnectedList = "SELECT * FROM connected";

    public static String selectConnectedWithUser =
            "SELECT u.login, u.fname, u.lname, i.src " +
                    "FROM ((connected c LEFT JOIN users u " +
                    "ON (u.login = c.toLogin AND :login = c.fromLogin) OR (u.login = c.fromLogin AND c.toLogin = :login)) " +
                    "INNER JOIN images i ON i.profileId = u.profileId AND i.avatar = TRUE) " +
                    "WHERE c.isConnected = TRUE AND u.login <> :login";


    public static String selectImageLikeEvent = "SELECT * FROM imageLikeEvents";
    public static String selectProfile = "SELECT * FROM profiles";
    public static String selectUsers = "SELECT * FROM users";

    public static String selectTEST =
            "SELECT * FROM locations l WHERE GET_DISTANCE(:x, :y, l.x, l.y) > 0";

    public static String selectUsersWithFilters =
            "SELECT u.login, u.fname, u.lname, p.age, l.x, l.y, i.src, r.rating, t.count as tagsCount, p.gender, p.preference, p.biography, GET_DISTANCE(:x, :y, l.x, l.y) as distance " +
                    "FROM ((((((users u INNER JOIN profiles p ON u.profileId = p.id) " +
                    "INNER JOIN locations l ON u.profileId = l.profileId) " +
                    "INNER JOIN rating r ON u.login = r.login) " +
                    "INNER JOIN (SELECT r.login as login, COUNT(r.login) as count FROM tagRelations r WHERE r.tagId IN (:tagIds) GROUP BY r.login) t  ON u.login = t.login) " +
                    "LEFT JOIN blacklist b ON b.fromLogin = :login AND b.toLogin = u.login) " +
                    "INNER JOIN images i ON i.profileId = u.profileId AND i.avatar = TRUE) " +
                    "WHERE (b.isBlocked IS NULL OR b.isBlocked <> TRUE) " +
                    "AND r.rating >= :ratingMin AND r.rating <= :ratingMax " +
                    "AND u.login <> :login " +
                    "AND p.gender IN (:preferenceGender) " +
                    "AND t.count > 0 " +
                    "AND p.age >= :ageMin AND p.age <= :ageMax " + //возраст
                    "AND GET_DISTANCE(:x, :y, l.x, l.y) <= :radius "; //растояние

    public static String selectUsersWithoutTagsWithFilters =
            "SELECT u.login, u.fname, u.lname, p.age, l.x, l.y, i.src, r.rating, p.gender, p.preference, p.biography, GET_DISTANCE(:x, :y, l.x, l.y) as distance " +
                    "FROM (((((users u INNER JOIN profiles p ON u.profileId = p.id) " +
                    "INNER JOIN locations l ON u.profileId = l.profileId) " +
                    "INNER JOIN rating r ON u.login = r.login) " +
                    "LEFT JOIN blacklist b ON b.fromLogin = :login AND b.toLogin = u.login) " +
                    "INNER JOIN images i ON i.profileId = u.profileId AND i.avatar = TRUE) " +
                    "WHERE (b.isBlocked IS NULL OR b.isBlocked <> TRUE) " +
                    "AND r.rating >= :ratingMin AND r.rating <= :ratingMax " +
                    "AND u.login <> :login " +
                    "AND p.gender IN (:preferenceGender) " +
                    "AND p.age >= :ageMin AND p.age <= :ageMax " + //возраст
                    "AND GET_DISTANCE(:x, :y, l.x, l.y) <= :radius "; //растояние

    public static String selectAllTags = "SELECT * FROM tags";
    public static String selectPopularTagsByLimit = "SELECT * FROM tags ORDER BY count DESC LIMIT :limit";
    public static String selectAllTagRelations = "SELECT * FROM tagRelations";

    public static String selectImagesCountById = "SELECT COUNT(*) FROM images WHERE id = :id";
    public static String selectLocationsCountById = "SELECT COUNT(*) FROM locations WHERE id = ?";
    public static String selectRatingCountById = "SELECT COUNT(*) FROM rating WHERE id = ?";
    public static String selectBlacklistCountById = "SELECT COUNT(*) FROM blacklist WHERE id = ?";
    public static String selectImageLikeEventsCountById = "SELECT COUNT(*) FROM imageLikeEvents WHERE id = ?";
    public static String selectProfilesCountById = "SELECT COUNT(*) FROM profiles WHERE id = :id";
    public static String selectTagIdByName = "SELECT id FROM tags WHERE name = :name";
    public static String selectUserTags = "SELECT t.name FROM tagRelations r inner join tags t WHERE r.login = :login AND r.tagId = t.id";
    public static String selectUserTagsOnlyIds = "SELECT t.id FROM tagRelations r inner join tags t WHERE r.login = :login AND r.tagId = t.id";
    public static String selectUsersWithCommonTags = "SELECT r.login as name, COUNT(r.login) as count FROM tagRelations r WHERE r.tagId IN (:tagIds) GROUP BY r.login";
    public static String selectBlackListCount = "SELECT COUNT(*) FROM blacklist WHERE fromLogin = :fromLogin AND toLogin = :toLogin";
    public static String selectUsersCountByLogin = "SELECT COUNT(*) FROM users WHERE login = :login";
    public static String selectUsersCountByActivationCode = "SELECT * FROM users WHERE activationCode = :activationCode";
    public static String selectUsersCountByLoginAndActivationCode = "SELECT COUNT(*) FROM users WHERE login = :login AND activationCode = :activationCode";
    public static String selectUserAndProfileByActivationCode = "SELECT * FROM users u INNER JOIN profiles p ON u.profileId = p.id WHERE activationCode = ?";
    public static String selectNewChatMessages = "SELECT * FROM chat WHERE toLogin = :toLogin AND fromLogin = :fromLogin AND read = FALSE";
    public static String selectCountAllNewChatMessages = "SELECT * FROM chat WHERE toLogin = :toLogin AND read = FALSE";
    public static String selectAllMessages = "SELECT * FROM chat";
    public static String selectChatMessages = "SELECT * FROM chat WHERE toLogin = :toLogin AND fromLogin = :fromLogin ORDER BY time DESC LIMIT :limit";
    public static String selectFullChatMessages = "SELECT * FROM chat WHERE (toLogin = :toLogin AND fromLogin = :fromLogin) OR (toLogin = :fromLogin AND fromLogin = :toLogin) ORDER BY time DESC LIMIT :limit";

    public static String selectImageById = "SELECT * FROM images WHERE id = :id LIMIT 1";
    public static String selectImageByProfileId = "SELECT * FROM images WHERE profileId = :profileId LIMIT 5";
    public static String selectLocationById = "SELECT * FROM locations WHERE id = ? LIMIT 1";
    public static String selectLocationByUser = "SELECT * FROM locations WHERE user = ?";
    public static String selectLocationByProfileId = "SELECT * FROM locations WHERE profileId = :profileId";
    public static String selectLastUserLocationByProfileId = "SELECT * FROM locations WHERE profileId = :profileId ORDER BY time DESC LIMIT 1";
    public static String selectLocations = "SELECT * FROM locations";
    public static String selectRatingById = "SELECT * FROM rating WHERE id = ? LIMIT 1";
    public static String selectBlacklistById = "SELECT * FROM blacklist WHERE id = ? LIMIT 1";
    public static String selectImageLikeEventById = "SELECT * FROM imageLikeEvents WHERE id = ? LIMIT 1";
    public static String selectEventByLogin = "SELECT * FROM events WHERE (type = :type1 OR type = :type2) AND login = :login AND data = :data ORDER BY time DESC LIMIT 1";
    public static String selectEventConnectedOrUnlike = "SELECT * FROM events WHERE (type = :type1 OR type = :type2) AND login = :login AND data = :data ORDER BY time DESC LIMIT 1";

    public static String selectHistoryEvents =
            "SELECT e.type, e.data as login, e.time, e.active, u.fname, u.lname, i.src, p.gender " +
                    "FROM (((events e INNER JOIN users u ON e.data = u.login) " +
                    "INNER JOIN images i ON u.profileId = i.profileId AND i.avatar = TRUE) " +
                    "INNER JOIN profiles p ON u.profileId = p.id) " +
                    "WHERE e.active = TRUE AND e.login = :login AND e.data <> '' AND e.data <> e.login " +
                    "ORDER BY time DESC LIMIT :limit OFFSET :offset";
    public static String selectNotificationEvents =
            "SELECT e.type, e.login as login, e.time, e.active, u.fname, u.lname, i.src, p.gender " +
                    "FROM ((((events e INNER JOIN users u ON u.login = e.login) " +
                    "INNER JOIN images i ON u.profileId = i.profileId AND i.avatar = TRUE) " +
                    "INNER JOIN profiles p ON p.id = u.profileId) " +
                    "LEFT JOIN blacklist b ON b.toLogin = e.login AND b.fromLogin = :data) " +
                    "WHERE (b.isBlocked IS NULL OR b.isBlocked <> TRUE) AND e.active = TRUE AND e.data = :data AND e.data <> e.login " +
                    "ORDER BY time DESC LIMIT :limit OFFSET :offset";

    public static String selectUserEventsCount = "SELECT COUNT(*) FROM events WHERE login = :login OR data = :login";
    public static String selectUnreadUserEventsCount = "SELECT COUNT(*) FROM events WHERE data = :login AND active = TRUE AND needShow = TRUE";

    public static String selectActiveLikes = "SELECT * FROM events WHERE (type = :type1 OR type = :type2) AND active = TRUE AND login = :login AND data = :data";

    public static String selectProfileById = "SELECT * FROM profiles WHERE id = :id LIMIT 1";
    public static String selectUserByLogin = "SELECT * FROM users WHERE login = :login LIMIT 1";
    public static String selectUserById = "SELECT * FROM users WHERE id = ? LIMIT 1";
    public static String selectUserByActivationCode = "SELECT * FROM users WHERE activationCode = :activationCode LIMIT 1";
    public static String selectUserProfileIdByLogin = "SELECT profileId FROM users WHERE login = :login LIMIT 1";
}
