package matcha.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import matcha.image.model.Image;
import matcha.location.model.Location;
import matcha.user.model.UserEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileWithoutEmail implements Serializable {

    private String login;
    private String fname;
    private String lname;
    private Integer age;
    private Integer gender;
    private List<Integer> preference;
    private String biography;
    private List<String> tags;
    private List<Image> images;
    private Date time;
    private Location location;
    private boolean isBlocked;
    private boolean isFilled;
    private boolean likedFrom;
    private boolean likedTo;

    public UserProfileWithoutEmail(UserEntity user, ProfileEntity profile, boolean isBlocked, boolean likedFrom, boolean likedTo) {
        this.login = user.getLogin();
        this.fname = user.getFname();
        this.lname = user.getLname();
        this.age = profile.getAge();
        this.gender = profile.getGender();
        this.preference = profile.getPreference();
        this.biography = profile.getBiography();
        this.tags = profile.getTags();
        this.images = profile.getImages();
        this.time = user.getTime();
        this.location = user.getLocation();
        this.isBlocked = isBlocked;
        this.isFilled = profile.isFilled();
        this.likedFrom = likedFrom;
        this.likedTo = likedTo;
    }
}
