package matcha.event.manipulation;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.event.db.EventDB;
import matcha.event.model.Event;
import matcha.event.model.EventWithUserInfo;

import java.util.List;

@Slf4j
public class EventManipulator {

    private final EventDB eventDB = new EventDB();

    public void insertEvent(Event event) {
        eventDB.insertEvent(event);
    }

    public void updateEventActiveById(Event event) {
        eventDB.updateEventActiveById(event);
    }

    public List<Event> getAllEvents() {
        return eventDB.getAllEvents();
    }

    //TODO Доделать, пока не понятно что должно быть
    public Event getLikeEvent(String fromLogin, String toLogin) {
//        try {
        return eventDB.getLikeEvent(fromLogin, toLogin);
//        } catch (Exception e) {
//            return new Event(EventType.IMAGE_LIKE, )
//        }
    }

    public boolean isLikeEvent(String fromLogin, String toLogin) {
        return eventDB.isLikeEvent(fromLogin, toLogin);
    }

    public List<EventWithUserInfo> getHistory(String fromLogin, Integer limit, Integer offset) {
        return eventDB.getHistory(fromLogin, limit, offset);
    }

    public List<EventWithUserInfo> getNotifications(String toLogin, Integer limit, Integer offset) {
        return eventDB.getNotifications(toLogin, limit, offset);
    }

    public List<Event> findActiveLikeOrUnlikeEvents(String login, String data) {
        return eventDB.findActiveLikeOrUnlikeEvents(login, data);
    }

    public boolean isConnectedEvent(String fromLogin, String toLogin) {
        return eventDB.isConnectedEvent(fromLogin, toLogin);
    }

    public Integer getUserActivityByLogin(String login) {
        return eventDB.getCountUserEventsByLogin(login);
    }

    public Integer getUnreadUserActivityByLogin(String login) {
        return eventDB.getCountUnreadUserEventsByLogin(login);
    }
}
