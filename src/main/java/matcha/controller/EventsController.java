package matcha.controller;

import matcha.event.model.Event;
import matcha.event.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

import static spark.Spark.get;

public class EventsController {

    private final EventService eventService = EventService.getInstance();

    public EventsController() {
        confirmRegistration();
    }

    public void confirmRegistration() {

        get("/events", (req, res) -> {
            res.type("text/html");
            List<Event> allEvents = eventService.getAllEvents();

            if (allEvents != null)
                return allEvents.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining());
            else
                return "<p>Filed to load events</p>";
        });
    }
}