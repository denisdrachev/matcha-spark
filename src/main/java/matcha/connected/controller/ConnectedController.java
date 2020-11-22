package matcha.connected.controller;

import matcha.user.service.UserService;
import matcha.validator.ValidationMessageService;

import static spark.Spark.get;

public class ConnectedController {

    private UserService userService = UserService.getInstance();
    private ValidationMessageService validationMessageService = ValidationMessageService.getInstance();

    public ConnectedController() {
        getUserConnected();
    }

    public void getUserConnected() {
        get("/connected", (req, res) -> userService.getUserConnected(req.headers("Authorization")));
    }
}
