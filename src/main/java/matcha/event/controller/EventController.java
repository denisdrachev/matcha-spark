package matcha.event.controller;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.blacklist.model.BlackListMessage;
import matcha.blacklist.service.BlackListService;
import matcha.event.model.EventWithUserInfo;
import matcha.event.service.EventService;
import matcha.profile.model.UserProfileWithoutEmail;
import matcha.response.Response;
import matcha.user.controller.UserController;
import matcha.user.model.UserEntity;
import matcha.user.service.UserService;
import matcha.validator.ValidationMessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static spark.Spark.exception;
import static spark.Spark.get;

@Slf4j
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RestController
//@NoArgsConstructor
//@RequestMapping
public class EventController {

    private EventService eventService = EventService.getInstance();
    private UserService userService = UserService.getInstance();
    private ValidationMessageService validationMessageService = ValidationMessageService.getInstance();



    public EventController() {
        getHistory();
        getNotification();
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
            return validationMessageService.prepareMessageOkData(new Gson().toJsonTree(history));
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
            List<EventWithUserInfo> history = eventService.getNotifications(userByToken.getLogin(), limit, offset);
            return validationMessageService.prepareMessageOkData(new Gson().toJsonTree(history));
        });
        exception(Exception.class, (exception, request, response) -> {
            response.body(validationMessageService.prepareErrorMessage(exception.getMessage()).toString());
        });
    }
}
