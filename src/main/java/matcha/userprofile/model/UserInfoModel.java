package matcha.userprofile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import matcha.image.model.Image;
import matcha.location.model.LocationLight;
import matcha.model.MyObject;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoModel implements Serializable, MyObject {

    @Length(min = 1, max = 20, message = "Допустимая длина имени от 1 до 20 символов")
    @NotBlank(message = "Имя не может быть пустым.")
    private String fname;

    @Length(min = 1, max = 20, message = "Допустимая длина фамилии от 1 до 20 символов")
    @NotBlank(message = "Фамилия не может быть пустой.")
    private String lname;

    @Email(message = "email не соответствует формату")
    @NotBlank(message = "email не может быть пустым.")
    @Length(min = 1, max = 30, message = "Допустимая длина email до 30 символов")
    private String email;

    @NotNull(message = "Значение поля 'age' не может быть пустым.")
    @Min(value = 18, message = "Возраст не может быть меньше 18.")
    @Max(value = 99, message = "Возраст не может быть больше 99.")
    private Integer age;

    @Min(value = 1, message = "Указан не верный гендер. Допустимые значения: 1,2,3")
    @Max(value = 3, message = "Указан не верный гендер. Допустимые значения: 1,2,3")
    @NotNull(message = "Значение поля 'gender' не может быть пустым.")
    private Integer gender;

    @Min(value = 1, message = "Поле 'Предпочтения' заполмнено некорректно")
    @Max(value = 3, message = "Поле 'Предпочтения' заполмнено некорректно")
    @NotNull(message = "Значение поля 'preference' не может быть пустым.")
    private Integer preference;

    @Length(min = 1, max = 200, message = "Допустимая длина биографии до 200 символов")
    @NotBlank(message = "Значение поля 'biography' не может быть пустым.")
    private String biography;

    @Size(min = 1, max = 5, message = "Количество тегов должно быть от 1 до 5")
    @NotNull(message = "Значение поля 'tags' не может быть пустым.")
    private List<String> tags;

    @NotNull(message = "Значение поля 'images' не может быть пустым.")
    @Size(min = 1, max = 5, message = "Количество изображений долюно быть от 1 до 5")
    private List<Image> images;

    @Valid
    private LocationLight location;
}
