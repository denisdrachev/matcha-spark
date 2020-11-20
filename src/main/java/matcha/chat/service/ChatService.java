package matcha.chat.service;

import lombok.AllArgsConstructor;
import matcha.chat.manipulation.ChatManipulator;
import matcha.chat.model.*;
import matcha.event.model.Event;
import matcha.event.service.EventService;
import matcha.response.Response;
import matcha.userprofile.model.UserProfileChat;
import matcha.userprofile.service.UserProfileService;
import matcha.utils.EventType;
import matcha.validator.ValidationMessageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChatService implements ChatInterface {

    private ChatManipulator chatManipulator;
    private UserProfileService userProfileService;
    private ValidationMessageService validationMessageService;
    private EventService eventService;


    @Override
    public Response saveMessage(ChatMessageSave chatMessage) {
        chatManipulator.insertChatMessage(chatMessage);
        Event newEvent = new Event(EventType.SEND_MESSAGE, chatMessage.getFromLogin(), false, chatMessage.getToLogin());
        eventService.saveNewEvent(newEvent);

        return validationMessageService.prepareMessageOkOnlyType();
    }

    //мб и не нужен этот функционал
    @Override
    public Response getMessages(String toLogin, String fromLogin, int limit) {
        List<ChatMessage> chatMessages = chatManipulator.getChatMessages(toLogin, fromLogin, limit);
        chatMessages.stream()
                .filter(chatMessage -> !chatMessage.isRead())
                .forEach(chatManipulator::updateChatMessage);
        return validationMessageService.prepareMessageOkDataList(chatMessages);
    }

    @Override
    public Response getFullMessages(ChatMessageFull message) {
        List<ChatMessage> chatMessages =
                chatManipulator.getFullChatMessages(message.getToLogin(), message.getFromLogin(), message.getLimit());
        chatMessages.stream()
                .filter(chatMessage -> !chatMessage.isRead())
                .forEach(chatManipulator::updateChatMessage);
        return validationMessageService.prepareMessageOkDataList(chatMessages);
    }

    @Override
    public Response getNewMessages(ChatNewMessageFromUser message) {
        List<ChatMessage> chatMessages = chatManipulator.getNewChatMessages(message.getToLogin(), message.getFromLogin());
        if (message.getIsRead() != 0)
            chatMessages.forEach(chatManipulator::updateChatMessage);
        return validationMessageService.prepareMessageOkDataList(chatMessages);
    }

    @Override
    public Response getAllNewMessages(ChatAllNewMessage message) {
        List<ChatMessage> chatMessages = chatManipulator.getAllNewChatMessages(message.getToLogin());
        List<UserProfileChat> collect = chatMessages.stream().map(chatMessage -> {
            UserProfileChat chatUserProfile = userProfileService.getChatUserProfile(chatMessage.getFromLogin());
            chatUserProfile.setChatMessages(chatMessage);
            return chatUserProfile;
        }).collect(Collectors.toList());
        return validationMessageService.prepareMessageOkDataList(collect);
    }

    public void checkChatUsersConnected(String toLogin, String fromLogin) {
        //пользователь toLogin лайнул хоть одну фотку пользователя fromLogin
        //пользователь fromLogin лайнул хоть одну фотку пользователя toLogin
    }
}
