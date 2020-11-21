package matcha.controller;

import lombok.RequiredArgsConstructor;
import matcha.blacklist.model.BlackListMessage;
import matcha.blacklist.service.BlackListService;
import matcha.location.model.Location;
import matcha.location.service.LocationService;
import matcha.user.model.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

import static spark.Spark.get;

//@Controller
//@RequiredArgsConstructor
public class LocationsController {

    private final LocationService locationService = LocationService.getInstance();
    private final BlackListService blackListService = BlackListService.getInstance();


    public LocationsController() {
        registration();
        getAllBlackList();
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
}