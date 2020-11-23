package matcha.db.crud;

import java.util.List;

public class Drop {

    public static String profiles = "DROP TABLE IF EXISTS profiles;";
    public static String events = "DROP TABLE IF EXISTS events;";
    public static String users = "DROP TABLE IF EXISTS users;";
    public static String blacklist = "DROP TABLE IF EXISTS blacklist;";
    public static String imageLikeEvents = "DROP TABLE IF EXISTS imageLikeEvents;";
    public static String images = "DROP TABLE IF EXISTS images;";
    public static String locations = "DROP TABLE IF EXISTS locations;";
    public static String rating = "DROP TABLE IF EXISTS rating;";
    public static String chat = "DROP TABLE IF EXISTS chat;";

    public static List<String> getAll() {
        return List.of(rating, locations, imageLikeEvents, blacklist, users, profiles, images, events);
    }
}
