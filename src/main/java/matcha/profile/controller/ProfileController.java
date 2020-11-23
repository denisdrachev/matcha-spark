package matcha.profile.controller;

import lombok.extern.slf4j.Slf4j;
import matcha.image.service.ImageService;
import matcha.profile.service.ProfileService;
import matcha.user.service.UserService;
import matcha.validator.ValidationMessageService;

import static spark.Spark.post;

@Slf4j
public class ProfileController {

    private ValidationMessageService validationMessageService = ValidationMessageService.getInstance();
    private ProfileService profileService = ProfileService.getInstance();
    private UserService userService = UserService.getInstance();
    private ImageService imageService = ImageService.getInstance();

    public ProfileController() {
        profileUpdate();
    }

    public void profileUpdate() {

        post("/profile-update", (req, res) -> userService.profileUpdate(req.headers("Authorization"), req.body()));
    }
}
