package matcha.user.controller;

import com.google.gson.Gson;
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

import static spark.Spark.*;


@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

    private static UserController userController;
    private ValidationMessageService validationMessageService = ValidationMessageService.getInstance();

    private UserController() {
    }

    public static UserController getInstance() {
        if (userController == null) {
            userController = new UserController();
        }
        return userController;
    }

    public void init() {
//        validationMessageService = ValidationMessageService.getInstance();
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
            return validationMessageService.prepareMessageOkData(new Gson().toJsonTree(userProfile));
        });


//        log.info("Request get user profile by login: {}", login);
//        UserProfileWithoutEmail userProfile = userService.getUserProfile(token, login);
//        return validationMessageService.prepareMessageOkData(userProfile);
    }
//
//    @GetMapping("/regitrationConfirm.html")
//    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {
//
//        Locale locale = request.getLocale();
//        boolean verificationToken = userService.activationUserByToken(token);
//
//        if (!verificationToken) {
//            String message = "auth.message.invalidToken"; //messages.getMessage("auth.message.invalidToken", null, locale);
//            model.addAttribute("message", message);
//            return "redirect:/badUser.html?lang=" + locale.getLanguage();
//        }
//        return "redirect:?lang=" + request.getLocale().getLanguage();
//    }
}
