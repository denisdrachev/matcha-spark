package matcha.event.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import matcha.event.manipulation.EventManipulator;
import matcha.event.model.Event;
import matcha.event.model.EventWithUserInfo;
import matcha.reactive.EventUnicastService;
import matcha.utils.EventType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class EventService {

    //TODO тут добавить реактивщину
    private EventUnicastService eventUnicastService;
    private EventManipulator eventManipulator = new EventManipulator();

    private static EventService eventService;

    public static EventService getInstance() {
        if (eventService == null) {
            eventService = new EventService();
        }
        return eventService;
    }

    public void init() {
//        registration();
    }

    public void saveNewEvent(Event event) {
        if (event == null)
            return;
        eventManipulator.insertEvent(event);
//        eventUnicastService.onNext(event);
    }

    public void updateEventActiveById(Event event) {
        if (event == null)
            return;
        eventManipulator.updateEventActiveById(event);
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

//    public List<Event> findActiveEvents(String type, String login, String data) {
//        return eventManipulator.findActiveLikeOrUnlikeEvents(type, login, data);
//    }

    public void setLikeOrUnlike(String fromLogin, String toLogin, int likeValue) {
        if (likeValue == 1) {
            Event eventLike = new Event(EventType.LIKE, fromLogin, true, toLogin);
            saveNewEvent(eventLike);
            boolean isBackLike = eventManipulator.isLikeEvent(toLogin, fromLogin);
            if (isBackLike) {
                Event eventConnectedTo = new Event(EventType.CONNECTED, fromLogin, true, toLogin);
                Event eventConnectedFrom = new Event(EventType.CONNECTED, toLogin, true, fromLogin);
                saveNewEvent(eventConnectedFrom);
                saveNewEvent(eventConnectedTo);
            }
        } else {
            boolean isConnected = eventManipulator.isConnectedEvent(fromLogin, toLogin);
            Event event = new Event(EventType.UNLIKE, fromLogin, true, toLogin);

            if (isConnected) {
                Event eventDisconnectedTo = new Event(EventType.DISCONNECTED, fromLogin, true, toLogin);
                Event eventDisconnectedFrom = new Event(EventType.DISCONNECTED, toLogin, true, fromLogin);
                saveNewEvent(eventDisconnectedTo);
                saveNewEvent(eventDisconnectedFrom);
            }
            saveNewEvent(event);
        }

//        List event = new Event(eventType, fromLogin, true, toLogin);
        //TODO надо ли это? мб можно просто смотреть последний лайк или дислайк
        /*activeEvents = eventManipulator.findActiveLikeOrUnlikeEvents(fromLogin, toLogin);
        activeEvents.forEach(event1 -> event1.setActive(false));
        activeEvents.forEach(this::updateEventActiveById);*/
//        saveNewEvent(event);
//        boolean isBackLike = eventManipulator.isLikeEvent(fromLogin, toLogin);
//        if (isBackLike) {
//
//        }
    }
}
