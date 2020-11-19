package matcha.db.crud;

public class Update {
    public static String updateImageById = "UPDATE images set src = :src, avatar = :avatar WHERE id = :id";
    public static String updateLocationById = "UPDATE locations set active = :active WHERE id = :id";
    public static String updateLocationByLogin = "UPDATE locations l set l.active = :active WHERE l.user = (SELECT id FROM users WHERE login = :login)";
    public static String updateRaitingById = "UPDATE rating set rating = ?, profile = ? WHERE id = ?";
    public static String updateBlacklistById = "UPDATE blacklist set isBlock = ? WHERE fromLogin = ? AND toLogin = ?";
    public static String updateImageLikeEventById = "UPDATE imageLikeEvents set active = ?, image = ?, who = ?, whom = ? WHERE id = ?";
    public static String updateProfileById = "UPDATE profiles set age = :age, gender = :gender, preference = :preference, biography = :biography, tags = :tags, isFilled = :isFilled WHERE id = :id";
    public static String updateUserById = "UPDATE users set login = :login, activationCode = :activationCode, fname = :fname, lname = :lname, email = :email, active = :active, blocked = :blocked, time = :time, profileId = :profileId WHERE id = :id";
    public static String updateUserByLogin = "UPDATE users set fname = :fname, lname = :lname, email = :email, time = :time WHERE login = :login";
    public static String updateChatMessage = "UPDATE chat set read = ? WHERE id = ?";
}
