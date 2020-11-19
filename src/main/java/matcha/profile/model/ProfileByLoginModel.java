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
public class ProfileByLoginModel implements Serializable {

    private Integer id;
    private Integer age = null;
    private Integer gender = null;
    private List<Integer> preference = new ArrayList<>();
    private String biography;
    private List<String> tags = new ArrayList<>();
    private List<Image> images = new ArrayList<>();
    private List<String> imagesIds;
    private Integer avatar = null;

    public ProfileByLoginModel(UserInfoModel userAndProfileUpdateModel) {
        this.age = userAndProfileUpdateModel.getAge();
        this.gender = userAndProfileUpdateModel.getGender();
        this.preference = userAndProfileUpdateModel.getPreference();
        this.biography = userAndProfileUpdateModel.getBiography();
        this.tags = userAndProfileUpdateModel.getTags();
        this.images = userAndProfileUpdateModel.getImages();
        this.avatar = userAndProfileUpdateModel.getAvatar();
    }
}
