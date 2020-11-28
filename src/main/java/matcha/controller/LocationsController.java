package matcha.controller;

import matcha.blacklist.model.BlackListMessage;
import matcha.blacklist.service.BlackListService;
import matcha.chat.model.ChatMessage;
import matcha.chat.service.ChatService;
import matcha.location.model.Location;
import matcha.location.service.LocationService;
import matcha.rating.model.Rating;
import matcha.rating.service.RatingService;
import matcha.response.Response;
import matcha.tag.model.Tag;
import matcha.tag.service.TagService;

import java.util.List;
import java.util.stream.Collectors;

import static matcha.converter.Utils.clearAllTables;
import static spark.Spark.get;

//@Controller
//@RequiredArgsConstructor
public class LocationsController {

    private final LocationService locationService = LocationService.getInstance();
    private final BlackListService blackListService = BlackListService.getInstance();
    private final TagService tagService = TagService.getInstance();
    private final RatingService ratingService = RatingService.getInstance();
    private final ChatService chatService = ChatService.getInstance();


    public LocationsController() {
        registration();
        getAllBlackList();
        getAllTags();
        getAllRatings();
        getAllChatMessages();
        clearTables();
    }

    private void clearTables() {

        get("/clear", (req, res) -> {
            clearAllTables();
//            if (allLocations != null)
                return "{\"type\": \"ok\"}";
//            else
//                return "{\"type\": \"error\"}";
        });

    }

//    @GetMapping("/locations")
//    public String confirmRegistration(Model model) {
//        List<Location> locationList = locationService.getAllLocations();
//        if (locationList != null)
//            model.addAttribute("name", locationList.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining()));
//        else
//            model.addAttribute("name", String.join("", "Filed to load locations"));
//        return "greeting";
//    }

    public void registration() {
        get("/locations", (req, res) -> {
            List<Location> allLocations = locationService.getAllLocations();
            if (allLocations != null)
                return allLocations.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining());
            else
                return "<p>Filed to load locations</p>";
        });
    }

    public void getAllBlackList() {
        get("/blacklist", (req, res) -> {
            List<BlackListMessage> allBlackList = blackListService.getAllBlackList();
            if (allBlackList != null)
                return allBlackList.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining());
            else
                return "<p>Filed to load blacklist</p>";
        });
    }

    public void getAllTags() {
        get("/tags", (req, res) -> {
            System.err.println("/tags");
            List<Tag> allTags = tagService.getAllTags();
            if (allTags != null)
                return allTags.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining());
            else
                return "<p>Filed to load tags</p>";
        });
    }

    public void getAllRatings() {
        get("/ratings", (req, res) -> {
            System.err.println("/ratings");
            List<Rating> allRatings = ratingService.getAllRatings();
            if (allRatings != null)
                return allRatings.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining());
            else
                return "<p>Filed to load ratings</p>";
        });
    }

    public void getAllChatMessages() {
        get("/chats", (req, res) -> {
            System.err.println("/chats");
            List<ChatMessage> allMessages = chatService.getAllMessages();
            if (allMessages != null)
                return allMessages.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining());
            else
                return "<p>Filed to load chat messages</p>";
        });
    }
}