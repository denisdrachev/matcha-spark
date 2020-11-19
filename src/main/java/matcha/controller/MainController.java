package matcha.controller;

import matcha.properties.Gateways;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {

    List<String> uris = new ArrayList<>();

    @PostConstruct
    private void init() {
        String url = "https://matcha-server.herokuapp.com/";
        String row = "<p th:text=\"${name}\" />";
        for (Gateways el : Gateways.values()) {
            System.err.println(url.concat(el.getUri()));
            uris.add("<p>".concat(url.concat(el.getUri())).concat("</p>"));
        }
    }

    @GetMapping("/")
    public String confirmRegistration(Model model) {
        System.err.println(uris.size());
        model.addAttribute("name", String.join("", uris));
        return "greeting";
    }

    @GetMapping("clearAllTables")
    public String clearAllTables(Model model) {


        model.addAttribute("name", "All tables are cleared!");
        return "greeting";
    }
}