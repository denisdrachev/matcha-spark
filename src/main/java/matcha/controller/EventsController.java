package matcha.controller;//package matcha.controller;

import lombok.RequiredArgsConstructor;
import matcha.event.model.Event;
import matcha.event.service.EventService;
import matcha.image.model.Image;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

import static spark.Spark.get;

//@Controller
//@RequiredArgsConstructor
public class EventsController {

    private final EventService eventService = EventService.getInstance();

    public EventsController() {
        confirmRegistration();
    }

    //    @GetMapping("/events")
//    public String confirmRegistration(Model model) {
//        List<Event> allEvents = eventService.getAllEvents();
//        if (allEvents != null)
//            model.addAttribute("name", allEvents.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining()));
//        else
//            model.addAttribute("name", String.join("", "Filed to load images"));
//        return "greeting";
//    }

    public void confirmRegistration() {
//        List<Image> imagesList = imageService.getAllImages();
//        if (imagesList != null)
//            model.addAttribute("name", imagesList.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining()));
//        else
//            model.addAttribute("name", String.join("", "Filed to load images"));
//        return "greeting";

        get("/events", (req, res) -> {
            List<Event> allEvents = eventService.getAllEvents();

            if (allEvents != null)
                return allEvents.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining());
            else
                return "<p>Filed to load events</p>";
        });
    }
}