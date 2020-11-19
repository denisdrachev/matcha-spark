package matcha.chat.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.chat.model.ChatAllNewMessage;
import matcha.chat.model.ChatMessageFull;
import matcha.chat.model.ChatMessageSave;
import matcha.chat.model.ChatNewMessageFromUser;
import matcha.chat.service.ChatService;
import matcha.response.Response;
import matcha.user.service.UserService;
import matcha.validator.ValidationMessageService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@AllArgsConstructor
@RequestMapping(value = "chat")
public class ChatController {

    private ChatService chatService;
    private UserService userService;
    private ValidationMessageService validationMessageService;

    @PostMapping(value = "/chat/save", produces = "application/json")
    public Response postChatMessage(@CookieValue(value = "token") String token,
                                    @RequestBody ChatMessageSave message) {
        log.info("Request save chat message: {}", message);
        Response response = validationMessageService.validateMessage(message);
        if (response != null) {
            return response;
        }
        userService.checkUserToToken(token);
        return chatService.saveMessage(message);
    }

//    @Deprecated
//    @GetMapping(value = "/{toLogin}/{fromLogin}/{limit}", produces = "application/json")
//    public Response getMessagesByLimit(@PathVariable String toLogin, @PathVariable String fromLogin, @PathVariable int limit) {
//        log.info("Chat GET message to {} from {} limit {}", toLogin, fromLogin, limit);
//        return chatService.getMessages(toLogin, fromLogin, limit);
//    }

    @GetMapping(value = "chat/full", produces = "application/json")
    public Response getFullMessagesByLimit(@CookieValue(value = "token") String token,
                                           @RequestBody ChatMessageFull message) {
        log.info("Request get full chat messages by limit: {}", message);
        Response response = validationMessageService.validateMessage(message);
        if (response != null) {
            return response;
        }
        userService.checkUserToToken(token);
        return chatService.getFullMessages(message);
    }

    @GetMapping(value = "chat/new", produces = "application/json")
    public Response getNewMessages(@CookieValue(value = "token") String token,
                                   @RequestBody ChatNewMessageFromUser message) {
        log.info("Request get new message from user: {}", message);
        Response response = validationMessageService.validateMessage(message);
        if (response != null) {
            return response;
        }
        userService.checkUserToToken(token);
        return chatService.getNewMessages(message);
    }

    @GetMapping(value = "new/{toLogin}", produces = "application/json")
    public Response getAllNewMessages(@CookieValue(value = "token") String token,
                                      @RequestBody ChatAllNewMessage message) {
        log.info("Request get new message from user: {}", message);
        Response response = validationMessageService.validateMessage(message);
        if (response != null) {
            return response;
        }
        userService.checkUserToToken(token);
        return chatService.getAllNewMessages(message);
    }
}
