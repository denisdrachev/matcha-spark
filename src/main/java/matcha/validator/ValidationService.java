package matcha.validator;

import lombok.extern.slf4j.Slf4j;
import matcha.exception.context.IncorrectInputParamsException;
import matcha.image.model.ImageEntity;

import java.util.List;

@Slf4j
public class ValidationService {

    public void validateAvatarInImages(Integer avatar, List<ImageEntity> imageElems) {
        if (avatar < 0 && imageElems != null && imageElems.size() > 0
                || avatar >= 0 && imageElems == null
                || avatar >= 0 && imageElems.size() == 0) {
            log.info("Error avatar value. Avatar: {} Images: {}", avatar, imageElems);
            throw new IncorrectInputParamsException("Данные по изображениям некорректны");
        }
    }

    public void validateAvatarIndexInImages(Integer avatar, List<ImageEntity> imageElems) {
        if (imageElems != null && (avatar >= imageElems.size())) {
            log.warn("Error. Avatar value [{}] out of index images [{}]", avatar, imageElems.size());
            throw new IncorrectInputParamsException();
        }
    }
}
