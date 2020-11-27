package matcha.userprofile.model;

import lombok.*;
import matcha.converter.Converter;
import matcha.image.model.Image;
import matcha.location.model.Location;
import matcha.model.MyObject;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoModel implements Serializable, MyObject {

    @NotBlank(message = "Значение поля 'login' не может быть пустым.")
    private String login;
    private String password;

    @Length(min = 1, max = 100, message = "Допустимая длина имени от 1 до 100 символов")
    @NotBlank(message = "Значение поля 'fname' не может быть пустым.")
    private String fname;

    @Length(min = 1, max = 100, message = "Допустимая длина фамилии от 1 до 100 символов")
    @NotBlank(message = "Значение поля 'lname' не может быть пустым.")
    private String lname;

    @Email(message = "Значение поля 'email' не соответствует формату")
    @NotBlank(message = "Значение поля 'email' не может быть пустым.")
    private String email;

    @NotNull(message = "Значение поля 'age' не может быть пустым.")
    @Min(value = 1, message = "Значение поля 'age' не может быть меньше 1.")
    @Max(value = 120, message = "Значение поля 'age' не может быть больше 120.")
    private Integer age;

    @Min(value = 1, message = "Указан не верный гендер.")
    @Max(value = 3, message = "Указан не верный гендер.")
    @NotNull(message = "Значение поля 'gender' не может быть пустым.")
    private Integer gender;

    @Min(value = 1, message = "Поле 'Предпочтения' заполмнено некорректно")
    @Max(value = 3, message = "Поле 'Предпочтения' заполмнено некорректно")
    @NotNull(message = "Значение поля 'preference' не может быть пустым.")
    private Integer preference;

    @NotBlank(message = "Значение поля 'biography' не может быть пустым.")
    private String biography;

    @Size(min = 1, message = "Не заполнено поле Теги")
    @NotNull(message = "Значение поля 'tags' не может быть пустым.")
    private List<String> tags;

    @NotNull(message = "Значение поля 'images' не может быть пустым.")
    @Size(min = 1, message = "Не заполнены изображения")
    private List<Image> images;

    private Date time = Calendar.getInstance().getTime();
    @Valid
    private Location location;
    private String activationCode;


//    @Override
//    public String toString() {
//        return Converter.objectToJson(this).get();
//    }

}
