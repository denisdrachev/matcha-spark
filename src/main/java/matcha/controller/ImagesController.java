package matcha.controller;//package matcha.controller;

import lombok.RequiredArgsConstructor;
import matcha.image.model.Image;
import matcha.image.service.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ImagesController {

    private final ImageService imageService;

    @GetMapping("/images")
    public String confirmRegistration(Model model) {
        List<Image> imagesList = imageService.getAllImages();
        if (imagesList != null)
            model.addAttribute("name", imagesList.stream().map(location -> "<p>" + location + "</p>").collect(Collectors.joining()));
        else
            model.addAttribute("name", String.join("", "Filed to load images"));
        return "greeting";
    }
}