package matcha.user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import matcha.converter.JsonTransformer;
import matcha.response.Response;
import matcha.user.model.UserInfo;
import matcha.user.model.UserRegistry;
import matcha.user.service.UserService;
import matcha.validator.ValidationMessageService;

import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;


@Slf4j
public class UserController {

    private ValidationMessageService validationMessageService = ValidationMessageService.getInstance();
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
    }

    private UserService userService = UserService.getInstance();

    public void registration() {
        post("/register", (req, res) -> {
            log.info("Request /register Body: {}", req.body());
            UserRegistry user = new Gson().fromJson(req.body(), UserRegistry.class);
            System.out.println(user);
            Response response = validationMessageService.validateMessage(user);
            if (response != null) {
                return response;
            }
            userService.userRegistration(user);
            return validationMessageService.prepareMessageOkOnlyType();
        }, new JsonTransformer());
    }

    public void resetPassword() {
        post("/reset-password", (req, res) -> userService.resetPasswordEmail(req.headers("Authorization")));
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

            String userLogin = userService.checkUserToToken(token);

            userService.updateTimeByLogin(userLogin);

            log.info("Request get user profile by login: {}", login);
            return validationMessageService.prepareMessageOkData(gson.toJsonTree(userService.getUserProfile(token, login)));
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

            String userLogin = userService.checkUserToToken(token);
            userService.updateTimeByLogin(userLogin);

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

            String token;
            try {
                Map<String, String> map = gson.fromJson(req.body(), Map.class);
                token = map.get("token");
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
