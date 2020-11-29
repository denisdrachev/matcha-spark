package matcha;

//import ch.qos.logback.classic.Level;
//import ch.qos.logback.classic.Logger;

import lombok.extern.slf4j.Slf4j;
import matcha.validator.ValidationMessageService;
import spark.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static spark.Spark.*;

//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import java.util.logging.Level;
//import java.util.logging.Logger;

//@SpringBootApplication

@Slf4j
public class Application {

    // this map is shared between sessions and threads, so it needs to be thread-safe (http://stackoverflow.com/a/2688817)
    static Map<Session, String> userUsernameMap = new ConcurrentHashMap<>();
    static int nextUserNumber = 1; //Used for creating the next username


    public static void main(String[] args) {

//        Thread thread = new Thread(Spark::awaitInitialization);
        port(getHerokuAssignedPort());

//        webSocket("/socket.io", EchoWebSocket.class);
//        init();


//        staticFileLocation("/public"); //index.html is served at localhost:4567 (default port)
        webSocket("/chat", ChatWebSocketHandler.class);
        init();
//        qqq qqq = new qqq();

        ValidationMessageService validationMessageService = ValidationMessageService.getInstance();

        get("/hello", (req, res) -> "Hello Heroku World");
        options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });

        before((request, res) -> {
            res.header("Access-Control-Allow-Origin", "http://192.168.29.73:3000");
            res.header("Access-Control-Allow-Credentials", "true");

            res.type("application/json");
        });
        exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
            String s = validationMessageService.prepareErrorMessage(exception.getMessage()).toString();
            if (s == null || s.isEmpty()) {
                response.body("Некорректные параметры запроса.");
            } else {
                response.body(validationMessageService.prepareErrorMessage(exception.getMessage()).toString());
            }
        });
        SingletonControllers.init();

    }


    public static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}
