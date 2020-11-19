package matcha.image.service;

import matcha.image.model.Image;

import java.util.List;

interface ImageInterface {

    void saveImage(Image image);

    List<Image> getImagesByProfileId(int userId);
}
