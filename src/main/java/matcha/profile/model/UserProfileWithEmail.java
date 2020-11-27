package matcha.profile.model;

import com.google.gson.annotations.Expose;
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
public class UserProfileWithEmail implements Serializable {

    @Expose
    private String login;
    @Expose
    private String fname;
    @Expose
    private String lname;
    @Expose
    private Integer age;
    @Expose
    private Integer gender;
    @Expose
    private Integer preference;
    @Expose
    private String biography;
    @Expose
    private List<String> tags;
    @Expose
    private List<Image> images;
    @Expose
    private Date time;
    @Expose
    private Location location;
    @Expose
    private boolean isBlocked;
    @Expose
    private boolean isFilled;
    @Expose
    private String email;
    @Expose
    private Integer rating;

    public UserProfileWithEmail(UserEntity user, ProfileEntity profile, boolean isBlocked, Integer rating, List<String> tags) {
        this.login = user.getLogin();
        this.fname = user.getFname();
        this.lname = user.getLname();
        this.age = profile.getAge();
        this.gender = profile.getGender();
        this.preference = profile.getPreference();
        this.biography = profile.getBiography();
        this.tags = tags;
        this.images = profile.getImages();
        this.time = user.getTime();
        this.location = user.getLocation();
        this.isBlocked = isBlocked;
        this.isFilled = profile.isFilled();
        this.email = user.getEmail();
        this.rating = rating;
    }
}
