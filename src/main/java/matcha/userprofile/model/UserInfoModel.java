package matcha.userprofile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import matcha.converter.Converter;
import matcha.image.model.Image;
import matcha.location.model.Location;
import matcha.model.MyObject;
import org.json.JSONObject;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoModel implements Serializable, MyObject {

    @NotBlank(message = "Значение поля 'login' не может быть пустым.")
    private String login;
    @NotBlank(message = "Значение поля 'password' не может быть пустым.")
    private String password;
    @NotBlank(message = "Значение поля 'fname' не может быть пустым.")
    private String fname;
    @NotBlank(message = "Значение поля 'lname' не может быть пустым.")
    private String lname;
    @Email(message = "Значение поля 'email' не соответствует формату")
    @NotBlank(message = "Значение поля 'email' не может быть пустым.")
    private String email;
    @NotNull(message = "Значение поля 'age' не может быть пустым.")
    @Min(value = 1, message = "Значение поля 'age' не может быть меньше 1.")
    @Max(value = 120, message = "Значение поля 'age' не может быть больше 120.")
    private Integer age;
    @NotNull(message = "Значение поля 'gender' не может быть пустым.")
    private Integer gender;
    @NotNull(message = "Значение поля 'preference' не может быть пустым.")
    private List<Integer> preference;
    @NotBlank(message = "Значение поля 'biography' не может быть пустым.")
    private String biography;
    @NotNull(message = "Значение поля 'tags' не может быть пустым.")
    private List<String> tags;
    @NotNull(message = "Значение поля 'images' не может быть пустым.")
    private List<Image> images;
    @NotNull(message = "Значение поля 'avatar' не может быть пустым.")
    private Integer avatar = -1;
    private Date time = Calendar.getInstance().getTime();
    @Valid
    @NotNull(message = "Поле 'location' не может быть пустым")
    private Location location;
    private String activationCode;

//    public UserAndProfileUpdate(UserEntity user, Profile profile) {
//        login = user.getLogin();
//        fname = user.getFname();
//        lname = user.getLname();
//        email = user.getEmail();
//        age = profile.getAge();
//        gender = profile.getGender();
//        preference = profile.getPreference();
//        biography = profile.getBiography();
//        tags = profile.getTags();
//        images = profile.getImages();
//        avatar = profile.getAvatar();
//        time = user.getTime();
//        location = user.getLocation();
//    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("login", login);
        jsonObject.put("fname", fname == null ? JSONObject.NULL : fname);
        jsonObject.put("lname", lname == null ? JSONObject.NULL : lname);
        jsonObject.put("email", email);
        jsonObject.put("age", age == null ? JSONObject.NULL : age);
        jsonObject.put("gender", gender == null ? JSONObject.NULL : gender);
        jsonObject.put("preference", preference);
        jsonObject.put("biography", biography == null ? JSONObject.NULL : biography);
        jsonObject.put("tags", tags);
        jsonObject.put("images", images);
        jsonObject.put("avatar", avatar == null ? JSONObject.NULL : avatar);
        jsonObject.put("time", time);

        JSONObject locationJson = new JSONObject();
        if (location != null) {
            locationJson.put("x", location.getX());
            locationJson.put("y", location.getY());
        }
        jsonObject.put("location", location == null ? JSONObject.NULL : locationJson);
        return jsonObject;
    }

    @Override
    public String toString() {
        return Converter.objectToJson(this).get();
    }

}
