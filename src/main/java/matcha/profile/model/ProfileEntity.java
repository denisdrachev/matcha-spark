package matcha.profile.model;

import lombok.*;
import matcha.image.model.Image;
import matcha.userprofile.model.UserInfoModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProfileEntity implements Serializable {

    private Integer id;
    private Integer age = null;
    private Integer gender = null;
    private Integer preference;
    private String biography;
    private List<Image> images = new ArrayList<>();
    private boolean isFilled = true;

    public ProfileEntity(int profileId, UserInfoModel userAndProfileUpdateModel) {
        this.id = profileId;
        this.age = userAndProfileUpdateModel.getAge();
        this.gender = userAndProfileUpdateModel.getGender();
        this.preference = userAndProfileUpdateModel.getPreference();
        this.biography = userAndProfileUpdateModel.getBiography();
        this.images = userAndProfileUpdateModel.getImages();
        this.isFilled = true;
    }

    public ProfileEntity(ProfileEntityWithoutImages profileEntityWithoutImages) {
        this.id = profileEntityWithoutImages.getId();
        this.age = profileEntityWithoutImages.getAge();
        this.gender = profileEntityWithoutImages.getGender();
        this.preference = profileEntityWithoutImages.getPreference();
        this.biography = profileEntityWithoutImages.getBiography();
        this.isFilled = profileEntityWithoutImages.isFilled();
    }
}
