package matcha.user.model;

import lombok.*;
import matcha.location.model.LocationLight;
import matcha.model.MyObject;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable, MyObject {

    @NotBlank(message = "Поле 'login' не может быть пустым")
    @Length(min = 1, max = 50, message = "Допустимая длина логина от 1 до 50 символов")
    private String login;

    @ToString.Exclude
    @NotBlank(message = "Поле 'password' не может быть пустым")
    @Length(min = 1, max = 50, message = "Допустимая длина пароля от 1 до 50 символов")
    private String password;

    @Valid
    @NotNull(message = "Поле 'location' не может быть пустым")
    private LocationLight location;
}
