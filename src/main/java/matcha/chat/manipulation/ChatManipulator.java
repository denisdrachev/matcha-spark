package matcha.chat.manipulation;

import lombok.extern.slf4j.Slf4j;
import matcha.chat.db.ChatDB;
import matcha.chat.model.ChatMessage;
import matcha.chat.model.ChatMessageSave;

import java.util.List;

@Slf4j
public class ChatManipulator {

    private final ChatDB chatDB = new ChatDB();

    public void insertChatMessage(ChatMessageSave chatMessage) {
        chatDB.insertChatMessage(chatMessage);
    }

    public void updateChatMessage(ChatMessage chatMessage) {
        chatDB.updateChatMessage(chatMessage);
    }

    public void updateChatMessagesByIds(List<Long> ids) {
        if (ids.size() > 0) {
            chatDB.updateChatMessagesByIds(ids);
        }
    }

    public List<ChatMessage> getChatMessages(String toLogin, String fromLogin, int limit) {
        return chatDB.getChatMessages(toLogin, fromLogin, limit);
    }

    public List<ChatMessage> getFullChatMessages(String toLogin, String fromLogin, int limit) {
        return chatDB.getFullChatMessages(toLogin, fromLogin, limit);
    }

    public List<ChatMessage> getNewChatMessages(String toLogin, String fromLogin) {
        return chatDB.getNewChatMessages(toLogin, fromLogin);
    }

    public List<ChatMessage> getAllNewChatMessages(String toLogin) {
        return chatDB.getAllNewChatMessages(toLogin);
    }

    public List<ChatMessage> getAllMessages() {
        return chatDB.getAllMessages();
    }
}
