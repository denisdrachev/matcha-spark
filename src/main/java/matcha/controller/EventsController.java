package matcha.controller;//package matcha.controller;

import lombok.RequiredArgsConstructor;
import matcha.event.model.Event;
import matcha.event.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class EventsController {

    private final EventService eventService;

    @GetMapping("/events")
    public String confirmRegistration(Model model) {
        List<Event> allEvents = eventService.getAllEvents();
        if (allEvents != null)
            model.addAttribute("name", allEvents.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining()));
        else
            model.addAttribute("name", String.join("", "Filed to load images"));
        return "greeting";
    }
}