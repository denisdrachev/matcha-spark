package matcha.user.model;

import lombok.*;
import matcha.location.model.Location;
import matcha.model.MyObject;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable, MyObject {

    @NotBlank(message = "Поле 'login' не может быть пустым")
    private String login;
    @ToString.Exclude
    @NotBlank(message = "Поле 'password' не может быть пустым")
    private String password;
    private Date time = Calendar.getInstance().getTime();
    private Integer profileId = null;
    @Valid
    @NotNull(message = "Поле 'location' не может быть пустым")
    private Location location;
}
