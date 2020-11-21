package matcha.profile.model;

import lombok.*;
import matcha.image.model.Image;
import matcha.userprofile.model.UserInfoModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProfileEntityWithoutImages implements Serializable {

    private Integer id;
    private Integer age = null;
    private Integer gender = null;
    private String preference;
    private String biography;
    private String tags;
    private boolean isFilled;
}
