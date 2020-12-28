package matcha.user.model;

import lombok.*;
import matcha.userprofile.model.UserInfoModel;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateEntity {

    private String login;
    private String fname;
    private String lname;
    private String email;
    private Date time = Calendar.getInstance().getTime();

    public UserUpdateEntity(UserInfoModel userRegistry, String login) {
        this.email = userRegistry.getEmail();
        this.fname = userRegistry.getFname();
        this.lname = userRegistry.getLname();
        this.login = login;
    }
}
