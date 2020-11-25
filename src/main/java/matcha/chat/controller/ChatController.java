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

    //TODO получение id сообщения, которое прочитано
//    public void postChatMessage() {
//        post("/chat/save", (req, res) -> userService.postChatMessage(req.headers("Authorization"), req.body()));
//    }

//    @Deprecated
//    @GetMapping(value = "/{toLogin}/{fromLogin}/{limit}", produces = "application/json")
//    public Response getMessagesByLimit(@PathVariable String toLogin, @PathVariable String fromLogin, @PathVariable int limit) {
//        log.info("Chat GET message to {} from {} limit {}", toLogin, fromLogin, limit);
//        return chatService.getMessages(toLogin, fromLogin, limit);
//    }

    public void getFullMessagesByLimit() {
        get("chat/full", (req, res) -> userService.getFullMessagesByLimit(req.headers("Authorization"), req.body()));
    }

    public void getNewMessages() {
        get("/chat/new", (req, res) -> userService.getNewMessages(req.headers("Authorization"), req.body()));
    }

    public void getAllNewMessages() {
        get("/new/:toLogin", (req, res) -> userService.getAllNewMessages(req.headers("Authorization"), req.body()));
    }
}
