package matcha.image.manipulation;

import lombok.extern.slf4j.Slf4j;
import matcha.image.db.ImageDB;
import matcha.image.model.Image;

import java.util.List;

@Slf4j
public class ImageManipulator {

    private final ImageDB imageDB = new ImageDB();

    public void saveImage(Image image) {
        imageDB.updateImageById(image);
    }

    public void insertImage(Image image) {
        imageDB.insertImage(image);
    }

    public Image getImageById(String imageId) {
        return imageDB.getImageById(imageId);
    }

    public void deleteImageById(String id) {
        imageDB.dropImageById(id);
    }

    public List<Image> getImagesByProfileId(int profileId) {
        return imageDB.getImagesByProfileId(profileId);
    }

    public List<Image> getAllImages() {
        return imageDB.getAllImages();
    }

    public void updateClearAvatarByProfileId(Integer profileId) {
        imageDB.updateClearAvatarByProfileId(profileId);
    }
}
