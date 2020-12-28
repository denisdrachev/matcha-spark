package matcha.chat.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import matcha.chat.manipulation.ChatManipulator;
import matcha.chat.model.*;
import matcha.event.model.Event;
import matcha.event.service.EventService;
import matcha.response.Response;
import matcha.userprofile.model.UserProfileChat;
import matcha.userprofile.service.UserProfileService;
import matcha.utils.EventType;
import matcha.validator.ValidationMessageService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ChatService implements ChatInterface {

    private ChatManipulator chatManipulator = new ChatManipulator();
    private UserProfileService userProfileService = UserProfileService.getInstance();
    private ValidationMessageService validationMessageService = ValidationMessageService.getInstance();
    private EventService eventService = EventService.getInstance();

    private static ChatService chatService;

    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .serializeNulls()
            .create();

    public static ChatService getInstance() {
        if (chatService == null) {
            chatService = new ChatService();
        }
        return chatService;
    }


    @Override
    public Response saveMessage(ChatMessageSave chatMessage) {
        chatManipulator.insertChatMessage(chatMessage);
        Event newEvent = new Event(EventType.SEND_MESSAGE, chatMessage.getFromLogin(), true, chatMessage.getToLogin(), true);
        eventService.saveNewEvent(newEvent);
        return validationMessageService.prepareMessageOkOnlyType();
    }

    @Deprecated
    @Override
    public Response getMessages(String toLogin, String fromLogin, int limit) {
        List<ChatMessage> chatMessages = chatManipulator.getChatMessages(toLogin, fromLogin, limit);
        chatMessages.stream()
                .filter(chatMessage -> !chatMessage.isRead())
                .forEach(chatManipulator::updateChatMessage);
        return validationMessageService.prepareMessageOkData(gson.toJsonTree(chatMessages));
    }

    /**
     * 1. Получает последние limit сообщений между пользователями toLogin и fromLogin
     * 2. Если среди них есть непрочитанные - обновляет их в базу как прочитанные
     * 3. Возвращает их
     */
    @Override
    public Response getFullMessages(ChatMessageFull message, String login) {
        List<ChatMessage> chatMessages =
                chatManipulator.getFullChatMessages(message.getToLogin(), message.getFromLogin(), message.getLimit());
        List<Long> ids = chatMessages.stream()
                .filter(chatMessage -> !chatMessage.isRead() && !chatMessage.getFromLogin().equals(login))
                .map(ChatMessage::getId)
                .collect(Collectors.toList());
        chatManipulator.updateChatMessagesByIds(ids);
        Collections.reverse(chatMessages);

        return validationMessageService.prepareMessageOkData(gson.toJsonTree(chatMessages));
    }

    /**
     * 1. Ищет все непрочитанные сообщения от toLogin к fromLogin
     * 2. Смотрит флаг во входящем запросе. Помечать ли полученные сообщения как прочитанные? Если да - помечает
     * 3. Возвращает их
     */
    @Override
    public Response getNewMessages(ChatNewMessageFromUser message) {
        List<ChatMessage> chatMessages = chatManipulator.getNewChatMessages(message.getToLogin(), message.getFromLogin());
        if (message.getIsRead() != 0) {
            List<Long> ids = chatMessages.stream()
                    .filter(chatMessage -> !chatMessage.isRead())
                    .map(ChatMessage::getId)
                    .collect(Collectors.toList());
            chatManipulator.updateChatMessagesByIds(ids);
        }
        Collections.reverse(chatMessages);
        return validationMessageService.prepareMessageOkData(gson.toJsonTree(chatMessages));
    }

    /**
     * 1. Ищет все непрочитанные сообщения toLogin
     * 2. Для каждого непрочитанного сообщения ищет профиль отправителя (?!) и объединяет с сообщением
     * 3. Возвращает это месиво
     * <p>
     * неэффективно и хз зачем вообще
     */
    @Override
    public Response getAllNewMessages(ChatAllNewMessage message) {
        List<ChatMessage> chatMessages = chatManipulator.getAllNewChatMessages(message.getToLogin());
        List<UserProfileChat> collect = chatMessages.stream().map(chatMessage -> {
            UserProfileChat chatUserProfile = userProfileService.getChatUserProfile(chatMessage.getFromLogin());
            chatUserProfile.setChatMessages(chatMessage);
            return chatUserProfile;
        }).collect(Collectors.toList());
        return validationMessageService.prepareMessageOkData(gson.toJsonTree(collect));
    }

    public List<ChatMessage> getAllMessages() {
        return chatManipulator.getAllMessages();
    }
}
