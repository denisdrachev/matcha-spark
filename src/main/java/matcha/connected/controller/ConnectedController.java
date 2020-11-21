package matcha.connected.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import matcha.blacklist.model.BlackListMessage;
import matcha.response.Response;
import matcha.user.service.UserService;
import matcha.validator.ValidationMessageService;
import org.springframework.web.bind.annotation.CrossOrigin;

import static spark.Spark.exception;
import static spark.Spark.post;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RestController
//@AllArgsConstructor
//@RequestMapping
public class ConnectedController {

    private UserService userService = UserService.getInstance();
    private ValidationMessageService validationMessageService = ValidationMessageService.getInstance();

    public ConnectedController() {
        saveBlackListMessage();
    }

//    @PostMapping(value = "/blacklist/save", produces = "application/json")
    public void saveBlackListMessage() {

        post("/blacklist/save", (req, res) -> {

            String token = req.cookie("token");

            if (token == null || token.isEmpty()) {
                log.info("Token: {} Пользователь не авторизован.", token);
                return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
            }

            BlackListMessage blackListMessage = new Gson().fromJson(req.body(), BlackListMessage.class);
            log.info("Request save to black list: {}", blackListMessage);

            Response response = validationMessageService.validateMessage(blackListMessage);
            if (response != null) {
                return response;
            }

            return userService.saveBlackList(token, blackListMessage);
        });
        exception(Exception.class, (exception, request, response) -> {
            response.body(validationMessageService.prepareErrorMessage(exception.getMessage()).toString());
        });
    }
}
