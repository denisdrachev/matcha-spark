package matcha.db.crud;

public class Update {
    public static String updateImageById = "UPDATE images set src = :src, avatar = :avatar WHERE id = :id";
    public static String updateLocationById = "UPDATE locations set active = :active, time = :time, x = :x, y = :y, userSet = :userSet " +
            "   WHERE profileId = :profileId AND " +
            "((:userSet = TRUE AND userSet = TRUE) OR userSet = FALSE)";
    public static String updateLocationByLogin = "UPDATE locations l set l.active = :active WHERE l.profileId = (SELECT id FROM users WHERE profileId = :profileId)";
    public static String updateRaitingById = "UPDATE rating set rating = ?, profile = ? WHERE id = ?";
    public static String updateBlacklistById = "UPDATE blacklist set isBlocked = :isBlocked WHERE fromLogin = :fromLogin AND toLogin = :toLogin";
    public static String updateConnected = "UPDATE connected set isConnected = :isConnected WHERE (fromLogin = :fromLogin OR fromLogin = :fromLogin2) AND (toLogin = :toLogin OR toLogin = :toLogin2)";
    public static String updateEventActiveById = "UPDATE events set active = :active WHERE id = :id";
    public static String updateEventNeedShowById = "UPDATE events set needShow = :needShow WHERE id = :id";
    public static String updateIncTagCountById = "UPDATE tags set count = count + 1 WHERE id = :id";
    public static String updateIncRatingByLogin = "UPDATE rating set rating = rating + 1 WHERE login = :login";
    public static String updateDecTagCountById = "UPDATE tags set count = count - 1 WHERE id = :id";
    public static String updateProfileById = "UPDATE profiles set age = :age, gender = :gender, preference = :preference, biography = :biography, isFilled = :isFilled WHERE id = :id";
    public static String updateUserById = "UPDATE users set login = :login, activationCode = :activationCode, fname = :fname, lname = :lname, email = :email, active = :active, blocked = :blocked, time = :time, profileId = :profileId WHERE id = :id";
    public static String updateUserByLogin = "UPDATE users set fname = :fname, lname = :lname, email = :email, time = :time WHERE login = :login";
    public static String updateChatMessage = "UPDATE chat set read = ? WHERE id = ?";

    public static String updateHistoryNeedShowEvents = "UPDATE events set needShow = FALSE " +
            "WHERE id IN (SELECT e.id FROM events e WHERE login = :login AND data <> '' AND data <> login " +
            "ORDER BY time DESC LIMIT :limit OFFSET :offset)";
    public static String updateNotificationNeedShowEvents = "UPDATE events SET needShow = FALSE " +
            "WHERE id IN (SELECT e.id FROM events e WHERE data = :data AND data <> login " +
            "ORDER BY time DESC LIMIT :limit OFFSET :offset)";
}
