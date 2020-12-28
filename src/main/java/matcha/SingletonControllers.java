package matcha;

import matcha.blacklist.controller.BlackListController;
import matcha.chat.controller.ChatController;
import matcha.connected.controller.ConnectedController;
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
        new UserController();
        new UsersController();
        new LocationsController();
        new ProfileController();
        new EventController();
        new ImagesController();
        new BlackListController();
        new EventsController();
        new ChatController();
        new ConnectedController();
        new SpringJdbcConfig();
    }
}
