package matcha.controller;

import matcha.properties.Gateways;
import java.util.ArrayList;
import java.util.List;


public class MainController {

    List<String> uris = new ArrayList<>();

    private void init() {
        String url = "https://matcha-server.herokuapp.com/";
        String row = "<p th:text=\"${name}\" />";
        for (Gateways el : Gateways.values()) {
            System.err.println(url.concat(el.getUri()));
            uris.add("<p>".concat(url.concat(el.getUri())).concat("</p>"));
        }
    }

//    public String confirmRegistration(Model model) {
//        System.err.println(uris.size());
//        model.addAttribute("name", String.join("", uris));
//        return "greeting";
//    }
//
//    @GetMapping("clearAllTables")
//    public String clearAllTables(Model model) {
//
//
//        model.addAttribute("name", "All tables are cleared!");
//        return "greeting";
//    }
}