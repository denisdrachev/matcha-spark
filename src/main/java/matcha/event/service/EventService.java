package matcha.event.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import matcha.event.manipulation.EventManipulator;
import matcha.event.model.Event;
import matcha.reactive.EventUnicastService;
import matcha.user.service.UserService;
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

    public void saveEvent(Event event) {
        if (event == null)
            return;
        eventManipulator.insertEvent(event);
//        eventUnicastService.onNext(event);
    }

    public List<Event> getAllEvents() {
        return eventManipulator.getAllEvents();
    }

    public boolean isLikeEvent(String fromLogin, String toLogin) {
        return eventManipulator.isLikeEvent(fromLogin, toLogin);
    }
}
