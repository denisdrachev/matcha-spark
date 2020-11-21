package matcha;

import matcha.blacklist.controller.BlackListController;
import matcha.controller.EventsController;
import matcha.controller.ImagesController;
import matcha.controller.LocationsController;
import matcha.controller.UsersController;
import matcha.db.SpringJdbcConfig;
import matcha.event.controller.EventController;
import matcha.profile.controller.ProfileController;
import matcha.user.controller.UserController;

public class SingletonControllers {

    public static void init() {

        UserController userController = new UserController();

        UsersController usersController = new UsersController();

        LocationsController locationsController = new LocationsController();

        ProfileController profileController = new ProfileController();

        EventController eventController = new EventController();

        ImagesController imagesController = new ImagesController();

        new BlackListController();

        new EventsController();

//        new SpringJdbcConfig();
    }
}
