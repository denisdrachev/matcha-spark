package matcha.db.crud;

public class Delete {

    public static String deleteUsers = "DELETE from users";
    public static String deleteTagRelations = "DELETE from tagRelations";
    public static String deleteTags = "DELETE from tags";
    public static String deleteConnected = "DELETE from connected";
    public static String deleteEvents = "DELETE from events";
    public static String deleteChat = "DELETE from chat";
    public static String deleteImageLikeEvents = "DELETE from imageLikeEvents";
    public static String deleteLocations = "DELETE from locations";
    public static String deleteBlacklist = "DELETE from blacklist";
    public static String deleteRating = "DELETE from rating";
    public static String deleteProfiles = "DELETE from profiles";
    public static String deleteImages = "DELETE from images";


    public static String deleteUserById = "DELETE from users WHERE id = ?;";
    public static String deleteUserByLogin = "DELETE from users WHERE login = ?;";

    public static String deleteProfileById = "DELETE from profiles WHERE id = :id;";

    public static String deleteImageById = "DELETE from images WHERE id = :id;";
    public static String deleteTagsRelationByLogin = "DELETE from tagRelations WHERE login = :login";
}
