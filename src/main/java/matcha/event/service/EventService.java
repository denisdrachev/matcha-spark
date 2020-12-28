package matcha.event.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import matcha.blacklist.model.BlackListMessage;
import matcha.blacklist.service.BlackListService;
import matcha.connected.model.ConnectedEntity;
import matcha.connected.service.ConnectedService;
import matcha.event.manipulation.EventManipulator;
import matcha.event.model.Event;
import matcha.event.model.EventWithUserInfo;
import matcha.rating.service.RatingService;
import matcha.utils.EventType;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.List;

import static matcha.ChatWebSocketHandler.webSocketConnection;

@Slf4j
public class EventService {

    private ConnectedService connectedService = ConnectedService.getInstance();
    private BlackListService blackListService = BlackListService.getInstance();
    private RatingService ratingService = RatingService.getInstance();
    private Gson gson = new Gson();


    private EventManipulator eventManipulator = new EventManipulator();
    private static EventService eventService;

    public static EventService getInstance() {
        if (eventService == null) {
            eventService = new EventService();
        }
        return eventService;
    }

    public void init() {
    }

    public void saveNewEvent(Event event) {
        if (event == null)
            return;
        if (EventType.LIKE.equals(event.getType())) {
            if (!isLikeEvent(event.getLogin(), event.getData())) {
                ratingService.incRatingByLogin(event.getData());
            }
        } else if (EventType.UNLIKE.equals(event.getType())) {
            if (isLikeEvent(event.getLogin(), event.getData())) {
                ratingService.decRatingByLogin(event.getData());
            }
        }
        eventManipulator.insertEvent(event);

        try {
            if ((EventType.LIKE.equals(event.getType()) ||
                    EventType.CONNECTED.equals(event.getType()) ||
                    EventType.DISCONNECTED.equals(event.getType()) ||
                    EventType.PROFILE_LOAD.equals(event.getType()) ||
                    EventType.SEND_MESSAGE.equals(event.getType())) &&
                    webSocketConnection.containsKey(event.getData())) {

                Session session = webSocketConnection.get(event.getData());
                session.getRemote().sendString(gson.toJson(event));
            }
        } catch (IOException e) {
            log.warn("Exception WebSocket Event sending.");
        }
    }

    public List<Event> getAllEvents() {
        return eventManipulator.getAllEvents();
    }

    public boolean isLikeEvent(String fromLogin, String toLogin) {
        return eventManipulator.isLikeEvent(fromLogin, toLogin);
    }

    public List<EventWithUserInfo> getHistory(String fromLogin, Integer limit, Integer offset) {
        return eventManipulator.getHistory(fromLogin, limit, offset);
    }

    public List<EventWithUserInfo> getNotifications(String toLogin, Integer limit, Integer offset) {
        return eventManipulator.getNotifications(toLogin, limit, offset);
    }

    public void setLikeOrUnlike(String fromLogin, String toLogin, int likeValue) {

        BlackListMessage blackListFrom = blackListService.getBlackListMessage(toLogin, fromLogin);

        if (likeValue == 1) {
            Event eventLike = new Event(EventType.LIKE, fromLogin, true, toLogin);
            saveNewEvent(eventLike);
            boolean isBackLike = eventManipulator.isLikeEvent(toLogin, fromLogin);
            if (isBackLike) {
                ConnectedEntity connected = connectedService.getConnected(fromLogin, toLogin);
                connected.setConnected(true);
                connectedService.saveConnected(connected);

                Event eventConnectedTo = new Event(EventType.CONNECTED, fromLogin, true, toLogin);
                Event eventConnectedFrom = new Event(EventType.CONNECTED, toLogin, !blackListFrom.isBlocked(),
                        fromLogin, !blackListFrom.isBlocked());
                saveNewEvent(eventConnectedFrom);
                saveNewEvent(eventConnectedTo);
            }
        } else {
            Event event = new Event(EventType.UNLIKE, fromLogin, false, toLogin);
            saveNewEvent(event);

            ConnectedEntity connected = connectedService.getConnected(fromLogin, toLogin);

            if (connected.isConnected()) {
                connected.setConnected(false);
                connectedService.saveConnected(connected);

                Event eventDisconnectedTo = new Event(EventType.DISCONNECTED, fromLogin, true, toLogin);
                Event eventDisconnectedFrom = new Event(EventType.DISCONNECTED, toLogin, !blackListFrom.isBlocked(),
                        fromLogin, !blackListFrom.isBlocked());
                saveNewEvent(eventDisconnectedTo);
                saveNewEvent(eventDisconnectedFrom);
            }
        }
    }

    public Integer getUnreadUserActivityByLogin(String login) {
        return eventManipulator.getUnreadUserActivityByLogin(login);
    }
}
