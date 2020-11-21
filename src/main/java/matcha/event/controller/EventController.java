package matcha.event.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import matcha.event.model.EventWithUserInfo;
import matcha.event.service.EventService;
import matcha.user.model.UserEntity;
import matcha.user.service.UserService;
import matcha.validator.ValidationMessageService;

import java.util.List;

import static spark.Spark.*;

@Slf4j
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RestController
//@NoArgsConstructor
//@RequestMapping
public class EventController {

    private EventService eventService = EventService.getInstance();
    private UserService userService = UserService.getInstance();
    private ValidationMessageService validationMessageService = ValidationMessageService.getInstance();
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .serializeNulls()
            .create();



    public EventController() {
        getHistory();
        getNotification();
        setUserLikeOrDislike();
    }

    public void getHistory() {
        get("/history/:limit/:offset", (req, res) -> {

            String token = req.cookie("token");

            int limit;
            int offset;

            try {
                limit = Integer.parseInt(req.params(":limit"));
                offset = Integer.parseInt(req.params(":offset"));
            } catch (Exception e) {
                return validationMessageService.prepareErrorMessage("Некорректные параметры запроса.");
            }


            if (token == null || token.isEmpty()) {
                return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
            }

            log.info("Request get history by token: {}", token);

            UserEntity userByToken = userService.getUserByToken(token);
            List<EventWithUserInfo> history = eventService.getHistory(userByToken.getLogin(), limit, offset);


//            UserProfileWithoutEmail userProfile = userService.getUserProfile(token, login);
            return validationMessageService.prepareMessageOkData(gson.toJsonTree(history));
        });
        exception(Exception.class, (exception, request, response) -> {
            response.body(validationMessageService.prepareErrorMessage(exception.getMessage()).toString());
        });
    }

    public void getNotification() {
        get("/notification/:limit/:offset", (req, res) -> {

            String token = req.cookie("token");
            int limit;
            int offset;

            try {
                limit = Integer.parseInt(req.params(":limit"));
                offset = Integer.parseInt(req.params(":offset"));
            } catch (Exception e) {
                return validationMessageService.prepareErrorMessage("Некорректные параметры запроса.");
            }

            if (token == null || token.isEmpty()) {
                return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
            }

            log.info("Request get history by token: {}", token);

            UserEntity userByToken = userService.getUserByToken(token);
            List<EventWithUserInfo> notifications = eventService.getNotifications(userByToken.getLogin(), limit, offset);
            return validationMessageService.prepareMessageOkData(gson.toJsonTree(notifications));
        });
        exception(Exception.class, (exception, request, response) -> {
            response.body(validationMessageService.prepareErrorMessage(exception.getMessage()).toString());
        });
    }

    public void setUserLikeOrDislike() {
        post("/like-user/:login/:value", (req, res) -> {

            String token = req.cookie("token");
            String toLogin = req.params(":login");
            int value;

            try {
                value = Integer.parseInt(req.params(":value"));
            } catch (Exception e) {
                return validationMessageService.prepareErrorMessage("Некорректные параметры запроса.");
            }

            if (token == null || token.isEmpty()) {
                return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
            }

            log.info("Request set user like or dislike login: {} value: {}", toLogin, value);

            UserEntity userByToken = userService.getUserByToken(token);
            eventService.setLikeOrUnlike(userByToken.getLogin(), toLogin, value);

            return validationMessageService.prepareMessageOkOnlyType();
        });
        exception(Exception.class, (exception, request, response) -> {
            response.body(validationMessageService.prepareErrorMessage(exception.getMessage()).toString());
        });
    }
}
