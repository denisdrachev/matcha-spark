package matcha.chat.controller;

import lombok.extern.slf4j.Slf4j;
import matcha.user.service.UserService;

import static spark.Spark.get;
import static spark.Spark.post;

@Slf4j
public class ChatController {

    private UserService userService = UserService.getInstance();

    public ChatController() {
        getFullMessagesByLimit();
        getNewMessages();
        getAllNewMessages();
        postChatMessage();
    }

    public void postChatMessage() {
        post("/chat/save", (req, res) -> userService.postChatMessage(req.headers("Authorization"), req.body()));
    }

    public void getFullMessagesByLimit() {
        get("chat/full", (req, res) -> userService.getFullMessagesByLimit(req.headers("Authorization"), req.queryParams("toLogin"), req.queryParams("limit")));
    }

    public void getNewMessages() {
        get("/chat/new", (req, res) -> userService.getNewMessages(req.headers("Authorization"), req.body()));
    }

    public void getAllNewMessages() {
        get("/chat/allnew", (req, res) -> userService.getAllNewMessages(req.headers("Authorization"), req.body()));
    }
}
