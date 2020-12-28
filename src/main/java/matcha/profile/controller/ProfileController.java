package matcha.profile.controller;

import lombok.extern.slf4j.Slf4j;
import matcha.user.service.UserService;

import static spark.Spark.post;

@Slf4j
public class ProfileController {

    private UserService userService = UserService.getInstance();

    public ProfileController() {
        profileUpdate();
    }

    public void profileUpdate() {
        post("/profile-update", (req, res) -> userService.profileUpdate(req.headers("Authorization"), req.body()));
    }
}
