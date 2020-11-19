package matcha.properties;

import java.util.ArrayList;
import java.util.List;

public class Properties {

    public static final String table_profiles_path = "src/main/resources/sql/tables/profiles.sql";
    public static final String table_users_path = "src/main/resources/sql/tables/users.sql";
    public static final String table_images_path = "src/main/resources/sql/tables/images.sql";
    public static final String table_events_path = "src/main/resources/sql/tables/events.sql";
    public static final String table_locations_path = "src/main/resources/sql/tables/locations.sql";
    public static final String table_imageLikeEvents_path = "src/main/resources/sql/tables/imageLikeEvents.sql";
    public static final String table_blacklist_path = "src/main/resources/sql/tables/blacklist.sql";
    public static final String table_rating_path = "src/main/resources/sql/tables/rating.sql";
    public static final String table_chat_path = "src/main/resources/sql/tables/chat.sql";

    public static List<String> getAllTablesPath() {
        List<String> list = new ArrayList<>();
        list.add(table_images_path);
        list.add(table_profiles_path);
        list.add(table_users_path);
        list.add(table_rating_path);
        list.add(table_blacklist_path);
        list.add(table_locations_path);
        list.add(table_imageLikeEvents_path);
        list.add(table_chat_path);
        list.add(table_events_path);
        return list;
    }
}
