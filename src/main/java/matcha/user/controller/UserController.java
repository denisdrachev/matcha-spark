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

import static spark.Spark.*;


@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

    private static UserController userController;
    private ValidationMessageService validationMessageService = ValidationMessageService.getInstance();
    private Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public UserController() {
        registration();
        login();
        getUserProfile();
    }

    private UserService userService = UserService.getInstance();

    //
//    @PostMapping(value = "register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    //Errors errors,
    public void registration() {
        post("/register", (req, res) -> {
            UserRegistry user = gson.fromJson(req.body(), UserRegistry.class);
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
            UserInfo user = gson.fromJson(req.body(), UserInfo.class);
            log.info("Income registration request. User: {}", user);

            Response response = validationMessageService.validateMessage(user);
            if (response != null) {
                return response;
            }

            return userService.userLogin(user);
        });

        exception(Exception.class, (exception, request, response) -> {
            response.body(validationMessageService.prepareErrorMessage(exception.getMessage()).toString());
        });
    }

    //
//    @GetMapping(value = "profile-get/{login}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void getUserProfile() {


        get("/profile-get/:login", (req, res) -> {

            String token = req.cookie("token");
            String login = req.params(":login");

            if (token == null || token.isEmpty()) {
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
//
//    @GetMapping("/regitrationConfirm.html")
    //TODO переделать в рабочую форму. Пока не работает
    public void confirmRegistration(/*WebRequest request, Model model, @RequestParam("token") String token*/) {

        get("/regitrationConfirm.html", (req, res) -> {

            String token = req.cookie("token");

            if (token == null || token.isEmpty()) {
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
