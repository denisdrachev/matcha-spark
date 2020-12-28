package matcha;

import lombok.extern.slf4j.Slf4j;
import matcha.exception.BaseException;
import matcha.properties.ConfigProperties;
import matcha.validator.ValidationMessageService;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static spark.Spark.*;

@Slf4j
public class Application {

    public static void main(String[] args) {
        for (String arg : args) {
            try {
                String[] split = arg.split("=");
                if ("active".equals(split[0])) {
                    boolean active = Boolean.parseBoolean(split[1]);
                    ConfigProperties.usersDefaultActive = active;
                } else if ("email".equals(split[0])) {
                    boolean isSendEmail = Boolean.parseBoolean(split[1]);
                    ConfigProperties.emailSend = isSendEmail;
                } else if ("baseUrl".equals(split[0])) {
                    String baseUrl = split[1];
                    ConfigProperties.baseUrl = baseUrl;
                } else if ("debug".equals(split[0])) {
                    boolean debugMode = Boolean.parseBoolean(split[1]);
                    ConfigProperties.debug = debugMode;
                }
            } catch (Exception e) {
            }
        }

        initDebug();
        initRoutes();
        SingletonControllers.init();
    }

    private static void initRoutes() {
        port(getHerokuAssignedPort());
        webSocket("/chat", ChatWebSocketHandler.class);
        init();

        ValidationMessageService validationMessageService = ValidationMessageService.getInstance();

        get("/hello", (req, res) -> "Hello Heroku World");
        options("/*", (request, response) -> {
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
            String s = validationMessageService.prepareErrorMessage(exception.getMessage()).toString();
            if (exception instanceof BaseException) {
                if (s == null || s.isEmpty()) {
                    response.body(s1);
                } else {
                    response.body(validationMessageService.prepareErrorMessage(exception.getMessage()).toString());
                }
            } else {
                response.body(s1);
            }
        });
    }

    private static void initDebug() {
        if (!ConfigProperties.debug) {
            Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            });
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
        log.info("Set debug mode: {}", ConfigProperties.debug);
        log.info("Set property base url: {}", ConfigProperties.baseUrl);
        log.info("Set property is send emails: {}", ConfigProperties.emailSend);
        log.info("Set property default active user: {}", ConfigProperties.usersDefaultActive);
    }

    public static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
