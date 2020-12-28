package matcha.controller;

import matcha.blacklist.model.BlackListMessage;
import matcha.blacklist.service.BlackListService;
import matcha.chat.model.ChatMessage;
import matcha.chat.service.ChatService;
import matcha.connected.model.ConnectedEntity;
import matcha.connected.service.ConnectedService;
import matcha.location.db.LocationDB;
import matcha.location.model.Location;
import matcha.location.service.LocationService;
import matcha.rating.model.Rating;
import matcha.rating.service.RatingService;
import matcha.tag.model.Tag;
import matcha.tag.service.TagService;

import java.util.List;
import java.util.stream.Collectors;

import static matcha.converter.Utils.clearAllTables;
import static spark.Spark.get;

public class LocationsController {

    private final LocationService locationService = LocationService.getInstance();
    private final BlackListService blackListService = BlackListService.getInstance();
    private final TagService tagService = TagService.getInstance();
    private final RatingService ratingService = RatingService.getInstance();
    private final ChatService chatService = ChatService.getInstance();
    private final ConnectedService connectedService = ConnectedService.getInstance();

    public LocationsController() {
        registration();
        getAllBlackList();
        getAllTags();
        getAllRatings();
        getAllChatMessages();
        getAllConnecteds();
        clearTables();
        getAllLoctest();
    }

    private void clearTables() {
        get("/clear", (req, res) -> {
            clearAllTables();
            return "{\"type\": \"ok\"}";
        });
    }

    public void registration() {
        get("/locations", (req, res) -> {
            res.type("text/html");
            List<Location> allLocations = locationService.getAllLocations();
            if (allLocations != null)
                return allLocations.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining());
            else
                return "<p>Filed to load locations</p>";
        });
    }

    public void getAllBlackList() {
        get("/blacklist", (req, res) -> {
            res.type("text/html");
            List<BlackListMessage> allBlackList = blackListService.getAllBlackList();
            if (allBlackList != null)
                return allBlackList.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining());
            else
                return "<p>Filed to load blacklist</p>";
        });
    }

    public void getAllLoctest() {
        get("/loctest", (req, res) -> {
            res.type("text/html");
            LocationDB locationDB = new LocationDB();
            double x = Double.parseDouble(req.queryParams("x"));
            double y = Double.parseDouble(req.queryParams("y"));
            List<Location> testLocation = locationDB.getTESTLocation(x, y);
            if (testLocation != null)
                return testLocation.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining());
            else
                return "<p>Filed to load loctest</p>";
        });
    }

    public void getAllTags() {
        get("/tags", (req, res) -> {
            res.type("text/html");
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
            res.type("text/html");
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
            res.type("text/html");
            System.err.println("/chats");
            List<ChatMessage> allMessages = chatService.getAllMessages();
            if (allMessages != null)
                return allMessages.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining());
            else
                return "<p>Filed to load chat messages</p>";
        });
    }

    public void getAllConnecteds() {
        get("/connecteds", (req, res) -> {
            res.type("text/html");
            System.err.println("/chats");
            List<ConnectedEntity> allConnected = connectedService.getAllConnected();
            if (allConnected != null)
                return allConnected.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining());
            else
                return "<p>Filed to load chat messages</p>";
        });
    }
}