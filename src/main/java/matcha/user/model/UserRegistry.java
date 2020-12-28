package matcha.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import matcha.location.model.LocationLight;
import matcha.model.MyObject;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString(exclude = {"password"})
@NoArgsConstructor
public class UserRegistry implements Serializable, MyObject {

    @Length(min = 1, max = 50, message = "Допустимая длина логина от 1 до 50 символов")
    @NotBlank(message = "Поле 'login' не может быть пустым")
    private String login;

    @Length(min = 1, max = 50, message = "Допустимая длина пароля от 1 до 50 символов")
    @NotBlank(message = "Поле 'password' не может быть пустым")
    private String password;

    @Length(min = 1, max = 50, message = "Допустимая длина имени от 1 до 50 символов")
    @NotBlank(message = "Поле 'fname' не может быть пустым")
    private String fname;

    @Length(min = 1, max = 50, message = "Допустимая длина фамилии от 1 до 50 символов")
    @NotBlank(message = "Поле 'lname' не может быть пустым")
    private String lname;

    @Length(max = 50, message = "Допустимая длина email до 50 символов")
    @NotBlank(message = "Поле 'email' не может быть пустым")
    @Email(message = "Поле 'email' не соответствует email формату")
    private String email;

    @Valid
    @NotNull(message = "Поле 'location' не может быть пустым")
    private LocationLight location;


    public UserRegistry(String login,
                        String password,
                        String fname,
                        String lname,
                        String email,
                        LocationLight location) {
        this.login = login;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.location = location;
    }
}
