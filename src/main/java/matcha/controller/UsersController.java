package matcha.controller;

import matcha.profile.model.ProfileEntityWithoutImages;
import matcha.profile.service.ProfileService;
import matcha.user.model.UserEntity;
import matcha.user.model.UserEntityFull;
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
            try {
                res.type("text/html");
                List<UserEntity> allUsers = userService.getAllUsers();
                List<UserEntityFull> collect = allUsers.stream().map(UserEntityFull::new).collect(Collectors.toList());
                if (collect != null)
                    return collect.stream().map(user -> "<p>" + user + "</p>").collect(Collectors.joining());
                else
                    return "<p>Filed to load users</p>";
            } catch (Exception e) {

            }
            return "";
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