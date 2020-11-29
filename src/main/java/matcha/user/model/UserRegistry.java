package matcha.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import matcha.location.model.Location;
import matcha.model.MyObject;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@ToString(exclude = {"password"})
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"time"})
public class UserRegistry implements Serializable, MyObject {
    @NotBlank(message = "Поле 'login' не может быть пустым")
    private String login;
    @NotBlank(message = "Поле 'password' не может быть пустым")
    private String password;
    @NotBlank(message = "Поле 'fname' не может быть пустым")
    private String fname;
    @NotBlank(message = "Поле 'lname' не может быть пустым")
    private String lname;
    @NotBlank(message = "Поле 'email' не может быть пустым")
    @Email(message = "Поле 'email' не соответствует email паттерну")
    private String email;
    private Date time = Calendar.getInstance().getTime();
    @Valid
    @NotNull(message = "Поле 'location' не может быть пустым")
    private Location location;

    public UserRegistry(@NotBlank(message = "Поле 'login' не может быть пустым") String login,
                        @NotBlank(message = "Поле 'password' не может быть пустым") String password,
                        @NotBlank(message = "Поле 'fname' не может быть пустым") String fname,
                        @NotBlank(message = "Поле 'lname' не может быть пустым") String lname,
                        @NotBlank(message = "Поле 'email' не может быть пустым")
                        @Email(message = "Поле 'email' не соответствует email паттерну") String email,
                        @Valid @NotNull(message = "Поле 'location' не может быть пустым") Location location) {
        this.login = login;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.location = location;
    }
}
