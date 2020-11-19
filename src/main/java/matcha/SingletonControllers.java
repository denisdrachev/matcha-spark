package matcha;

import matcha.controller.LocationsController;
import matcha.controller.UsersController;
import matcha.user.controller.UserController;

public class SingletonControllers {
    public static void init() {
        UserController userController = UserController.getInstance();
        userController.init();

        UsersController usersController = new UsersController();
        usersController.init();

        LocationsController locationsController = new LocationsController();
        locationsController.init();

    }
}
