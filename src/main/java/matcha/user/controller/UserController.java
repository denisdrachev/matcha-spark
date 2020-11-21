package matcha.user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.profile.model.UserProfileWithoutEmail;
import matcha.response.Response;
import matcha.user.model.UserInfo;
import matcha.user.model.UserRegistry;
import matcha.user.service.UserService;
import matcha.validator.ValidationMessageService;
import org.ietf.jgss.GSSContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Map;

import static spark.Spark.*;


@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

    private static UserController userController;
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
    }

    private UserService userService = UserService.getInstance();

    //
//    @PostMapping(value = "register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    //Errors errors,
    public void registration() {
        post("/register", (req, res) -> {
            UserRegistry user = new Gson().fromJson(req.body(), UserRegistry.class);
            log.info("Income registration request. User: {}", user);
            Response response = validationMessageService.validateMessage(user);
            if (response != null) {
                return response;
            }
            userService.userRegistration(user);
            return validationMessageService.prepareMessageOkOnlyType();
        });

        exception(Exception.class, (exception, request, response) -> {
            response.body(validationMessageService.prepareErrorMessage(exception.getMessage()).toString());
        });
    }

    //
//    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void login() {
        post("/login", (req, res) -> {
            UserInfo user = new Gson().fromJson(req.body(), UserInfo.class);
            log.info("Income registration request. User: {}", user);

            Response response = validationMessageService.validateMessage(user);
            if (response != null) {
                return response;
            }

            Response response1 = userService.userLogin(user);
            res.cookie("/", "token", "TEST_TEST_TEST", 3600, false, true);
            return response1;
        });

        exception(Exception.class, (exception, request, response) -> {
            response.body(validationMessageService.prepareErrorMessage(exception.getMessage()).toString());
        });
    }

    //
//    @GetMapping(value = "profile-get/{login}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void getUserProfile() {


        get("/profile-get/:login", (req, res) -> {
            log.info("Request /profile-get/:login");
//            String token = req.cookie("token");
            String token = req.headers("Authorization");
//            req.headers("Auth")
//            Map<String, String> cookies = req.cookies();
//
//            for (Map.Entry<String, String> entry : cookies.entrySet()) {
//                System.err.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
//            }

            String login = req.params(":login");

            if (token == null || token.isEmpty()) {
                log.info("Token: {} Пользователь не авторизован.", token);
                return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
            }

            log.info("Request get user profile by login: {}", login);
            UserProfileWithoutEmail userProfile = userService.getUserProfile(token, login);
            return validationMessageService.prepareMessageOkData(gson.toJsonTree(userProfile));
        });
        exception(Exception.class, (exception, request, response) -> {
            response.body(validationMessageService.prepareErrorMessage(exception.getMessage()).toString());
        });
    }

    public void getUserProfileSelf() {


        get("/profile-get", (req, res) -> {
            log.info("Request /profile-get");
//            String token = req.cookie("token");
            String token = req.headers("Authorization");
//            req.headers("Auth")
//            Map<String, String> cookies = req.cookies();
//
//            for (Map.Entry<String, String> entry : cookies.entrySet()) {
//                System.err.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
//            }

            if (token == null || token.isEmpty()) {
                log.info("Token: {} Пользователь не авторизован.", token);
                return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
            }

            log.info("Request get self user profile");
            UserProfileWithoutEmail userProfile = userService.getUserProfile(token, null);
            return validationMessageService.prepareMessageOkData(gson.toJsonTree(userProfile));
        });
        exception(Exception.class, (exception, request, response) -> {
            response.body(validationMessageService.prepareErrorMessage(exception.getMessage()).toString());
        });
    }

    //
//    @GetMapping("/regitrationConfirm.html")
    //TODO переделать в рабочую форму. Пока не работает
    public void confirmRegistration(/*WebRequest request, Model model, @RequestParam("token") String token*/) {

        get("/regitrationConfirm.html", (req, res) -> {

//            String token = req.cookie("token");
            String token = req.headers("Authorization");

            if (token == null || token.isEmpty()) {
                log.info("Token: {} Пользователь не авторизован.", token);
                return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
            }

//            Locale locale = req.getLocale();
            boolean verificationToken = userService.activationUserByToken(token);

            if (!verificationToken) {
                String message = "auth.message.invalidToken"; //messages.getMessage("auth.message.invalidToken", null, locale);
//                model.addAttribute("message", message);
//                return "redirect:/badUser.html?lang=" + locale.getLanguage();
//                return "redirect:/badUser.html?lang=ru";
                res.redirect("/badUser.html?lang=ru");
            }
            res.redirect("/redirect:?lang=ru");
            return "";
//            return "redirect:?lang=" + request.getLocale().getLanguage();
        });
        exception(Exception.class, (exception, request, response) -> {
            response.body(validationMessageService.prepareErrorMessage(exception.getMessage()).toString());
        });
    }
}
