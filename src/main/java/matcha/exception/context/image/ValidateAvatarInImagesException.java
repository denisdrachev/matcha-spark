package matcha.exception.context.image;


public class ValidateAvatarInImagesException extends RuntimeException {
    public ValidateAvatarInImagesException() {
        super("Данные по изображениям некорректны");
    }
}
