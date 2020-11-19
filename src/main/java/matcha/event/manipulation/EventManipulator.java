package matcha.event.manipulation;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.event.db.EventDB;
import matcha.event.model.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
//@RequiredArgsConstructor
@NoArgsConstructor
public class EventManipulator {

    private final EventDB eventDB = new EventDB();

    public void insertEvent(Event event) {
        eventDB.insertEvent(event);
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
}
