package matcha;

import matcha.event.model.Event;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.converters.UUIDConverter;
import org.sql2o.quirks.NoQuirks;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Sql2oModel  {

    private static Sql2o sql2o = null;

    public static Sql2o getSql2o() {
        if (sql2o == null) {
            sql2o = init();
        }
        return sql2o;
    }

    private Sql2oModel() {

    }

    private static Sql2o init() {
        String connectionString = "jdbc:h2:mem:matcha;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT from 'classpath:sql/tables/all.sql'";
        Sql2o mySql2o = new Sql2o(connectionString,
                "root", "root", new NoQuirks() {
            {
                System.err.println("OK!");
                // make sure we use default UUID converter.
//                converters.put(UUID.class, new UUIDConverter());
            }
        });
        return mySql2o;
//        uuidGenerator = new RandomUuidGenerator();
    }

//    @Override
    public void createPost(String title, String content, List<String> categories) {
        try (Connection conn = sql2o.beginTransaction()) {
//            UUID postUuid = uuidGenerator.generate();
            conn.createQuery("insert into events(type, login, time, active, data) VALUES (:type, :login, :time, :active, :data)")
                    .addParameter("type", "postUuid")
                    .addParameter("login", title)
                    .addParameter("active", true)
                    .addParameter("time", new Timestamp(System.currentTimeMillis()))
                    .addParameter("data", "sdasd")
                    .executeUpdate();
//            categories.forEach((category) ->
//                    conn.createQuery("insert into posts_categories(post_uuid, category) VALUES (:post_uuid, :category)")
//                            .addParameter("post_uuid", "postUuid")
//                            .addParameter("category", category)
//                            .executeUpdate());
            conn.commit();
//            return UUID.fromString("postUuid");
        }
    }

    public List<Event> getAllPosts() {
        try (Connection conn = sql2o.open()) {
            List<Event> posts = conn.createQuery("select * from events")
                    .executeAndFetch(Event.class);
//            posts.forEach((post) -> post.setCategories(getCategoriesFor(conn, post.getPost_uuid())));
            return posts;
        }
    }
}
