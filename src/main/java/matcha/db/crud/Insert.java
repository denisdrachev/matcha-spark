package matcha.db.crud;

public class Insert {

    public static String insertImage = "INSERT INTO images (index, src, profileId, avatar) VALUES (:index, :src, :profileId, :avatar)";
    public static String insertTag = "INSERT INTO tags (name, count) VALUES (:name, 1)";
    public static String insertTagRelation = "INSERT INTO tagRelations (login, tagId) VALUES (:login, :tagId)";
    public static String insertEvent = "INSERT INTO events (type, login, time, active, data, needShow) VALUES (:type, :login, :time, :active, :data, :needShow)";
    public static String insertLocation = "INSERT INTO locations (profileId, x, y, time, active, userSet) VALUES (:profileId, :x, :y, :time, :active, :userSet)";
    public static String insertRaiting = "INSERT INTO rating (rating, profile) VALUES (?, ?)";
    public static String insertBlacklist = "INSERT INTO blacklist (fromLogin, toLogin, isBlocked) VALUES (:fromLogin, :toLogin, :isBlocked)";
    public static String insertRating = "INSERT INTO rating (rating, login) VALUES (:rating, :login)";
    public static String insertConnected = "INSERT INTO connected (fromLogin, toLogin, isConnected) VALUES (:fromLogin, :toLogin, :isConnected)";
    public static String insertImageLikeEvent = "INSERT INTO imageLikeEvents (active, image, who, whom) VALUES (?, ?, ?, ?)";
    public static String insertProfile = "INSERT INTO profiles (age, gender, preference, biography, isFilled) " +
            "VALUES (:age, :gender, :preference, :biography, :isFilled)";
    public static String insertUser = "INSERT INTO users (login, password, activationCode, fname, lname, email, active, blocked, time, salt, profileId) " +
            "VALUES (:login, :password, :activationCode, :fname, :lname, :email, :active, :blocked, :time, :salt, :profileId)";
    public static String insertChatMessage = "INSERT INTO chat (toLogin, fromLogin, message, time, read) VALUES (?, ?, ?, ?, ?)";

}
