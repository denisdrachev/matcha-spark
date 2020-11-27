package matcha.image.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import matcha.exception.context.image.ValidateAvatarInImagesException;
import matcha.image.manipulation.ImageManipulator;
import matcha.image.model.Image;

import java.util.List;

public class ImageService implements ImageInterface {

    private ImageManipulator imageManipulator = new ImageManipulator();

    private static ImageService imageService;

    public static ImageService getInstance() {
        if (imageService == null) {
            imageService = new ImageService();
        }
        return imageService;
    }

    @Override
    public void saveImage(Image image) {
        imageManipulator.saveImage(image);
    }

    public void insertImage(Image image) {
        imageManipulator.insertImage(image);
    }

    @Override
    public List<Image> getImagesByProfileId(int profileId) {
        return imageManipulator.getImagesByProfileId(profileId);
    }

    public List<Image> getAllImages() {
        return imageManipulator.getAllImages();
    }

    public void createNewImages(int profileId) {
        for (int i = 0; i < 5; i++)
            insertImage(new Image(i, "", profileId, i == 0));
    }

    public void saveImages(List<Image> images) {
        images.forEach(this::saveImage);
    }

    public void checkImagesIsCorrect(List<Image> images) {
        if (images.stream().filter(Image::isAvatar).count() != 1) throw new ValidateAvatarInImagesException();
        if (images.stream().filter(image -> image.getIndex() >= 0 && image.getIndex() <= 4).count() != images.size())
            throw new ValidateAvatarInImagesException();
        //TODO добавить провеоку на уникальность индексов
    }

    public void clearAvatarValueBoProfileId(Integer profileId) {
        imageManipulator.updateClearAvatarByProfileId(profileId);
    }
}
