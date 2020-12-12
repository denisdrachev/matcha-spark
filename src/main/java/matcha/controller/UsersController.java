package matcha.controller;

import matcha.profile.model.ProfileEntityWithoutImages;
import matcha.profile.service.ProfileService;
import matcha.user.model.UserEntity;
import matcha.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import static spark.Spark.get;

public class UsersController {

    private final UserService userService = UserService.getInstance();
    private final ProfileService profileService = ProfileService.getInstance();

    public UsersController() {
        registration();
        getAllProfiles();
    }

    public void registration() {
        get("/users", (req, res) -> {
            res.type("text/html");
            List<UserEntity> allUsers = userService.getAllUsers();
            if (allUsers != null)
                return allUsers.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining());
            else
                return "<p>Filed to load users</p>";
        });
    }

    public void getAllProfiles() {
        get("/profiles", (req, res) -> {
            res.type("text/html");
            List<ProfileEntityWithoutImages> allProfiles = profileService.getAllProfiles();
            if (allProfiles != null)
                return allProfiles.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining());
            else
                return "<p>Filed to load blacklist</p>";
        });
    }
}