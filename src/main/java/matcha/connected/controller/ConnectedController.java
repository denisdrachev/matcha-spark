package matcha.connected.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import matcha.blacklist.model.BlackListMessage;
import matcha.response.Response;
import matcha.user.service.UserService;
import matcha.validator.ValidationMessageService;
import org.springframework.web.bind.annotation.CrossOrigin;

import static spark.Spark.*;

public class ConnectedController {

    private UserService userService = UserService.getInstance();
    private ValidationMessageService validationMessageService = ValidationMessageService.getInstance();

    public ConnectedController() {
        getUserConnected();
    }

    public void getUserConnected() {

        get("/connected", (req, res) -> userService.getUserConnected(req.headers("Authorization")));
        exception(Exception.class, (exception, request, response) -> {
            response.body(validationMessageService.prepareErrorMessage(exception.getMessage()).toString());
        });
    }
}
