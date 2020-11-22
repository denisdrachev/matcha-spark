package matcha.event.controller;

import lombok.extern.slf4j.Slf4j;
import matcha.user.service.UserService;

import static spark.Spark.get;
import static spark.Spark.post;

@Slf4j
public class EventController {

    private UserService userService = UserService.getInstance();

    public EventController() {
        getHistory();
        getNotifications();
        likeUser();
        getUnreadUserEvents();
        fakeUser();
    }

    public void getHistory() {
        get("/history", (req, res) ->
                userService.getHistory(
                        req.headers("Authorization"),
                        req.queryParams("limit"),
                        req.queryParams("offset")
                )
        );
    }

    public void getNotifications() {
        get("/notification", (req, res) ->
                userService.getNotifications(
                        req.headers("Authorization"),
                        req.queryParams("limit"),
                        req.queryParams("offset"))
        );
    }

    public void likeUser() {
        post("/like-user/:login/:value", (req, res) ->
                userService.likeUser(
                        req.headers("Authorization"),
                        req.params(":login"),
                        req.params(":value")
                )
        );
    }

    public void getUnreadUserEvents() {
        get("/new-notifications", (req, res) -> userService.getUnreadUserEvents(req.headers("Authorization")));
    }

    public void fakeUser() {
        post("/fake-user", (req, res) -> userService.fakeUserMessage(req.headers("Authorization"), req.body()));
    }
}
