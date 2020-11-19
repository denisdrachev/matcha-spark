package matcha.user.model;

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
public class UserRegistry implements Serializable, MyObject {
    @NotBlank(message = "Поле 'login' не может быть пустым")
    private String login;
    @NotBlank(message = "Поле 'password' не может быть пустым")
    private String password;
    @NotBlank(message = "Поле 'fname' не может быть пустым")
    private String fname;
    @NotBlank(message = "Поле 'lname' не может быть пустым")
    private String lname;
    @Email(message = "Поле 'email' не соответствует email паттерну")
    private String email;
    private Date time = Calendar.getInstance().getTime();
    @Valid
    @NotNull(message = "Поле 'location' не может быть пустым")
    private Location location;
}
