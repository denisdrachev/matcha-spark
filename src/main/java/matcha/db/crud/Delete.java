package matcha.db.crud;

public class Delete {

    public static String deleteUserById = "DELETE from users WHERE id = ?;";
    public static String deleteUserByLogin = "DELETE from users WHERE login = ?;";

    public static String deleteProfileById = "DELETE from profiles WHERE id = :id;";

    public static String deleteImageById = "DELETE from images WHERE id = :id;";
    public static String deleteTagsRelationByLogin = "DELETE from tagRelations WHERE login = :login";
}
