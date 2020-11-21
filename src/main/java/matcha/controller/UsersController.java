package matcha.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import matcha.response.Response;
import matcha.user.controller.UserController;
import matcha.user.model.UserEntity;
import matcha.user.model.UserRegistry;
import matcha.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

import static spark.Spark.*;

//@Controller
//@RequiredArgsConstructor
public class UsersController {

    private final UserService userService = UserService.getInstance();

//    private UsersController() {
//    }
//
//    public static UsersController getInstance() {
//        if (userController == null) {
//            userController = new UsersController();
//        }
//        return userController;
//    }

    public UsersController() {
        registration();
    }


//    @GetMapping("/users")
//    public String confirmRegistration(Model model) {
////        List<Location> locationList = locationService.getAllLocations();
//        List<UserEntity> allUsers = userService.getAllUsers();
//        if (allUsers != null)
//            model.addAttribute("name", allUsers.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining()));
//        else
//            model.addAttribute("name", String.join("", "Filed to load users"));
//        return "greeting";
//    }

    public void registration() {
        get("/users", (req, res) -> {

            List<UserEntity> allUsers = userService.getAllUsers();
//            if (allUsers != null)
//                model.addAttribute("name", allUsers.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining()));
//            else
//                model.addAttribute("name", String.join("", "Filed to load users"));
//            return "greeting";
            if (allUsers != null)
            return allUsers.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining());
            else
                return "<p>Filed to load users</p>";
        });

//        exception(Exception.class, (exception, request, response) -> {
//            response.body(validationMessageService.prepareErrorMessage(exception.getMessage()).toString());
//        });
    }
}