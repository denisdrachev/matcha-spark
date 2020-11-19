package matcha.reactive;

import matcha.event.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@EnableScheduling
@Component
public class EventGenerator {

    private AtomicInteger counter = new AtomicInteger(0);

    private EventUnicastService eventUnicastService;

    @Autowired
    public EventGenerator(EventUnicastService eventUnicastService) {
        this.eventUnicastService = eventUnicastService;
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    public void generateEvent() {
        int count = counter.getAndIncrement();
        EventSocket event = new EventSocket("event", count);

        Event ev = new Event("type", "login", null, true, "ddddaaaaattttaaaa");
        eventUnicastService.onNext(ev);
//        eventUnicastService.onNext(event);
//        System.err.println(event);
    }
}