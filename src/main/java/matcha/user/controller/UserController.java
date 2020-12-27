package matcha.user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import matcha.Application;
import matcha.converter.JsonTransformer;
import matcha.response.Response;
import matcha.user.model.UserEntity;
import matcha.user.model.UserInfo;
import matcha.user.model.UserRegistry;
import matcha.user.service.UserService;
import matcha.validator.ValidationMessageService;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;


@Slf4j
public class UserController {

    private ValidationMessageService validationMessageService = ValidationMessageService.getInstance();
    private List<String> passwords;
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .serializeNulls()
            .create();

    public UserController() {
        registration();
        login();
        getUserProfile();
        getUserProfileSelf();
        getUsers();
        logout();
        testExecute();
        resetPassword();
        changeResetPassword();
        confirmRegistration();
        validatePassword();
        init();
    }

    private void init() {
        try {
//            byte[] data;
//            try (InputStream in = getClass().getResourceAsStream("/elasticsearch/segmentsIndex.json")) {
//                data = in.readAllBytes(); // usable in Java 9+
//                // data = IOUtils.toByteArray(in); // uses Apache commons IO library
//            }
//            log.info("java.class.path: " + System.getProperty("java.class.path"));
//            log.info(Application.class.getProtectionDomain().getCodeSource().getLocation().getPath());
//            log.info(getClass().getResource("password").getPath());
//            Path path2 = Paths.get(Application.class.getProtectionDomain().getCodeSource().getLocation().getPath());
//            log.info("path2.getFileName(): " + path2.getFileName());
//            URL password = getClass().getClassLoader().getResource("password");
//            log.info("url: {}", password.getPath());
//            log.info("!!!!!!: {}\n\n", Paths.get("/passwords").getFileName());
            InputStream stream = Application.class.getClassLoader().getResourceAsStream("passwords");
            this.passwords = IOUtils.readLines(stream, "UTF-8");
            stream.close();
            log.info("Read passwords done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UserService userService = UserService.getInstance();

    public void registration() {
        post("/register", (req, res) -> {
            log.info("Request /register Body: {}", req.body());
            UserRegistry user = new Gson().fromJson(req.body(), UserRegistry.class);
            Response response = validationMessageService.validateMessage(user);
            if (response != null) {
                return response;
            }
            if (passwords != null && passwords.contains(user.getPassword())) {
                return validationMessageService.prepareErrorMessage("Указанный пароль скомпрометирован, укажите другой пароль");
            }
            userService.userRegistration(user);
            return validationMessageService.prepareMessageOkOnlyType();
        }, new JsonTransformer());
    }

    public void resetPassword() {
        post("/reset-password", (req, res) -> userService.resetPasswordEmail(req.body()));
    }

    public void changeResetPassword() {
        post("/change-reset-password", (req, res) -> userService.resetPassword(req.headers("Authorization"), req.body()));
    }

    //
    public void login() {
        post("/login", (req, res) -> {
            log.info("Request /login {}", req.body());
            UserInfo user = new Gson().fromJson(req.body(), UserInfo.class);

            Response response = validationMessageService.validateMessage(user);
            if (response != null) {
                return response;
            }

            return userService.userLogin(user);
        });
    }

    public void testExecute() {
        post("/test", (req, res) -> userService.getTestExecute());
    }

    public void getUserProfile() {

        get("/profile-get/:login", (req, res) -> {
            log.info("Request /profile-get/:login");
            String token = req.headers("Authorization");
            String login = req.params(":login");

            if (token == null || token.isEmpty()) {
                log.info("Token: {} Пользователь не авторизован.", token);
                return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
            }

            UserEntity user = userService.checkUserToToken(token);

            userService.updateTimeByLogin(user.getLogin());

            log.info("Request get user profile by login: {}", login);
            return validationMessageService.prepareMessageOkData(gson.toJsonTree(userService.getUserProfile(token, login)));
        });
    }

    public void validatePassword() {
        get("/password-validate/:password", (req, res) -> {
            String password = req.params(":password");
            log.info("Request /password-validate/{}", password);
            if (passwords != null && passwords.contains(password)) {
                return validationMessageService.prepareErrorMessage("");
            }
            return validationMessageService.prepareMessageOkOnlyType();
        });
    }

    public void getUserProfileSelf() {

        get("/profile-get", (req, res) -> {
            log.info("Request /profile-get");

            String token = req.headers("Authorization");


            if (token == null || token.isEmpty()) {
                log.info("Token: {} Пользователь не авторизован.", token);
                return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
            }

            UserEntity user = userService.checkUserToToken(token);
            userService.updateTimeByLogin(user.getLogin());

            log.info("Request get self user profile");
            return validationMessageService.prepareMessageOkData(gson.toJsonTree(userService.getUserProfile(token, null)));
        });
    }

    public void getUsers() {
        get("/get-users", (req, res) -> {
                    return userService.getUsers(
                            req.headers("Authorization"),
                            req.queryString()
                    );
                }
        );
    }

    public void logout() {
        get("/logout", (req, res) -> userService.userLogout(req.headers("Authorization")));
    }

    public void confirmRegistration() {
        get("/registration-check", (req, res) -> {
            log.info("Request /registration-check {}", req.queryString());
            String token;
            try {
                token = req.queryParams("token");
                if (token == null || token.isEmpty()) {
                    return validationMessageService.prepareErrorMessage("Невалидный токен");
                }
            } catch (Exception e) {
                return validationMessageService.prepareErrorMessage("Некорректные параметры запроса");
            }

            boolean verificationToken = userService.activationUserByToken(token);

            if (!verificationToken) {
                return validationMessageService.prepareErrorMessage("Невалидный токен");
            }
            return validationMessageService.prepareMessageOkOnlyType();
        });
    }
}
