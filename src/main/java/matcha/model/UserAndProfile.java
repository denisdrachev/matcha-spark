package matcha.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import matcha.converter.Converter;
import matcha.image.model.Image;
import matcha.location.model.Location;
import matcha.profile.model.ProfileEntity;
import matcha.user.model.UserEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
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
//        tags = profile.getTags();
        images = profile.getImages();
        time = user.getTime();
        location = user.getLocation();
    }
//
//    public JSONObject toJSONObject() {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("login", login);
//        jsonObject.put("fname", fname == null ? JSONObject.NULL : fname);
//        jsonObject.put("lname", lname == null ? JSONObject.NULL : lname);
//        jsonObject.put("email", email);
//        jsonObject.put("age", age == null ? JSONObject.NULL : age);
//        jsonObject.put("gender", gender == null ? JSONObject.NULL : gender);
//        jsonObject.put("preference", preference);
//        jsonObject.put("biography", biography == null ? JSONObject.NULL : biography);
//        jsonObject.put("tags", tags);
//        jsonObject.put("images", images);
//        jsonObject.put("avatar", avatar == null ? JSONObject.NULL : avatar);
//        jsonObject.put("time", time);
//
//        JSONObject locationJson = new JSONObject();
//        if (location != null) {
//            locationJson.put("x", location.getX());
//            locationJson.put("y", location.getY());
//        }
//        jsonObject.put("location", location == null ? JSONObject.NULL : locationJson);
//        return jsonObject;
//    }

    @Override
    public String toString() {
        return Converter.objectToJson(this).get();
    }

}
