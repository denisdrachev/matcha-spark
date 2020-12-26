package matcha;

//import ch.qos.logback.classic.Level;
//import ch.qos.logback.classic.Logger;

import lombok.extern.slf4j.Slf4j;
import matcha.exception.BaseException;
import matcha.properties.ConfigProperties;
import matcha.validator.ValidationMessageService;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static spark.Spark.*;

//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import java.util.logging.Level;
//import java.util.logging.Logger;

//@SpringBootApplication

@Slf4j
public class Application {

    // this map is shared between sessions and threads, so it needs to be thread-safe (http://stackoverflow.com/a/2688817)
//    static Map<Session, String> userUsernameMap = new ConcurrentHashMap<>();
//    static int nextUserNumber = 1; //Used for creating the next username


    public static void main(String[] args) {
//        System.setProperty(org.slf4j.simple.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");

        System.out.println("log.isDebugEnabled(): " + log.isDebugEnabled());
        System.out.println("log.isInfoEnabled(): " + log.isInfoEnabled());
        System.out.println("log.isWarnEnabled(): " + log.isWarnEnabled());
        System.out.println("log.isErrorEnabled(): " + log.isErrorEnabled());

        log.trace("trace");
        log.info("info");
        log.warn("warn");
        log.error("error");

        if (!ConfigProperties.debug) {
            Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            });
//        LogManager.getLogManager().reset();
            System.setOut(new PrintStream(new OutputStream() {
                @Override
                public void write(int arg0) throws IOException {

                }
            }));
            System.setErr(new PrintStream(new OutputStream() {
                @Override
                public void write(int arg0) throws IOException {

                }
            }));
        }

        for (String arg : args) {
            try {
                String[] split = arg.split("=");
                if ("active".equals(split[0])) {
                    boolean active = Boolean.parseBoolean(split[1]);
                    ConfigProperties.usersDefaultActive = active;
                    log.info("Set property default active user: {}", active);
                } else if ("email".equals(split[0])) {
                    boolean isSendEmail = Boolean.parseBoolean(split[1]);
                    ConfigProperties.emailSend = isSendEmail;
                    log.info("Set property is send emails: {}", isSendEmail);
                } else if ("baseUrl".equals(split[0])) {
                    String baseUrl = split[1];
                    ConfigProperties.baseUrl = baseUrl;
                    log.info("Set property base url: {}", baseUrl);
                } /*else if ("port".equals(split[0])) {
                    String basePort = split[1];
                    ConfigProperties.basePort = basePort;
                    log.info("Set property base port: {}", basePort);
                }*/
            } catch (Exception e) {
                log.info("incorrect argument: {}", arg);
            }
        }

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
                    String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
                    }
                    return "OK";
                });

        before((request, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Credentials", "true");
            res.type("application/json");
        });
        String s1 = validationMessageService.prepareErrorMessage("Некорректные параметры запроса").toString();
        exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
            String s = validationMessageService.prepareErrorMessage(exception.getMessage()).toString();
            if (exception instanceof BaseException) {
                if (s == null || s.isEmpty()) {
                    response.body(s1);
                } else {
                    response.body(validationMessageService.prepareErrorMessage(exception.getMessage()).toString());
                }
            } else {

                response.body(s1);
                exception.printStackTrace();
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
