package matcha.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import matcha.image.model.Image;
import matcha.location.model.Location;
import matcha.profile.model.ProfileEntity;
import matcha.user.model.UserEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAndProfile implements Serializable {

    private String login;
    private String fname;
    private String lname;
    private String email;
    private Integer age;
    private Integer gender;
    private Integer preference;
    private String biography;
    private List<String> tags;
    private List<Image> images;
    private Date time;
    private Location location;

    public UserAndProfile(UserEntity user, ProfileEntity profile) {
        login = user.getLogin();
        fname = user.getFname();
        lname = user.getLname();
        email = user.getEmail();
        age = profile.getAge();
        gender = profile.getGender();
        preference = profile.getPreference();
        biography = profile.getBiography();
        images = profile.getImages();
        time = user.getTime();
        location = user.getLocation();
    }
}
