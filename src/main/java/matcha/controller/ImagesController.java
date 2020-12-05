package matcha.controller;//package matcha.controller;

import matcha.image.model.Image;
import matcha.image.service.ImageService;

import java.util.List;
import java.util.stream.Collectors;

import static spark.Spark.get;

public class ImagesController {

    private final ImageService imageService = ImageService.getInstance();

    public ImagesController() {
        confirmRegistration();
    }

    public void confirmRegistration() {

        get("/images", (req, res) -> {
            res.type("text/html");
            List<Image> imagesList = imageService.getAllImages();
            if (imagesList != null)
                return imagesList.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining());
            else
                return "<p>Filed to load images</p>";
        });
    }
}