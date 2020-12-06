package matcha.chat.service;

import matcha.chat.model.ChatAllNewMessage;
import matcha.chat.model.ChatMessageFull;
import matcha.chat.model.ChatMessageSave;
import matcha.chat.model.ChatNewMessageFromUser;
import matcha.response.Response;

public interface ChatInterface {

    Response saveMessage(ChatMessageSave chatMessage);

    @Deprecated
    Response getMessages(String toLogin, String fromLogin, int limit);

    Response getFullMessages(ChatMessageFull message, String login);

    Response getNewMessages(ChatNewMessageFromUser message);

    Response getAllNewMessages(ChatAllNewMessage message);
}
