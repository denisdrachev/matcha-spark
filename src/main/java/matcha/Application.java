package matcha;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import static spark.Spark.port;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        Logger root = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);

//        jdbc:h2:mem:matcha
//        String connectionString = "jdbc:h2:mem:matcha;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT from 'classpath:sql/tables/all.sql'";
////        private val inputH2Url = "jdbc:h2:mem:inputDb;MODE=MSSQLServer;USER=sa;DB_CLOSE_DELAY=-1"
//
//
////                "jdbc:h2:~/reviews.db;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
//        Sql2o sql2o = new Sql2o(connectionString,
//                "root", "root", new PostgresQuirks() {
//            {
//                System.err.println("OK!");
//                // make sure we use default UUID converter.
//                converters.put(UUID.class, new UUIDConverter());
//            }
//        });

/*

        List<String> strs = new ArrayList<>();
        strs.add("asdasd");
        Sql2oModel model = new Sql2oModel();
        */
/*UUID post = *//*

        model.createPost("das", "sdasd", strs);
        */
/*UUID post = *//*
model.createPost("das", "sdasd", strs);
        */
/*UUID post = *//*
model.createPost("das", "sdasd", strs);
        */
/*UUID post = *//*
model.createPost("das", "sdasd", strs);
//        System.err.println(post);

        List<Event> allPosts = model.getAllPosts();
        System.err.println(allPosts.size());
        allPosts.forEach(System.out::println);
//        SpringApplication.run(Application.class, args);

*/
        port(80);
        SingletonControllers.init();
    }

}
