package matcha.controller;//package matcha.controller;

import lombok.RequiredArgsConstructor;
import matcha.image.model.Image;
import matcha.image.service.ImageService;
import matcha.location.model.Location;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

import static spark.Spark.get;

//@Controller
//@RequiredArgsConstructor
public class ImagesController {

    private final ImageService imageService = ImageService.getInstance();

    public ImagesController() {
        confirmRegistration();
    }

//    @GetMapping("/images")
    public void confirmRegistration() {
//        List<Image> imagesList = imageService.getAllImages();
//        if (imagesList != null)
//            model.addAttribute("name", imagesList.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining()));
//        else
//            model.addAttribute("name", String.join("", "Filed to load images"));
//        return "greeting";

        get("/images", (req, res) -> {

            List<Image> imagesList = imageService.getAllImages();
            if (imagesList != null)
                return imagesList.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining());
            else
                return "<p>Filed to load images</p>";
        });
    }
}