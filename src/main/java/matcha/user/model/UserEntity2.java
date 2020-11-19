package matcha.user.model;

import lombok.*;
import matcha.location.model.Location;
import matcha.model.MyObject;
import matcha.userprofile.model.UserInfoModel;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@ToString(exclude = {"password", "passwordBytes", "activationCode", "salt"})
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity2 implements Serializable, MyObject {

    private int id;
    private String login;
    private byte[] password;
    private byte[] passwordBytes;
    private String activationCode;
    private String fname;
    private String lname;
    private String email;
    private boolean active;
    private boolean blocked;
    private Date time = Calendar.getInstance().getTime();
    private byte[] salt;
    private Integer profileId = null;
    private Location location;

//    public UserEntity2(UserRegistry userRegistry) {
//        this.email = userRegistry.getEmail();
//        this.fname = userRegistry.getFname();
//        this.lname = userRegistry.getLname();
//        this.login = userRegistry.getLogin();
//        this.password = userRegistry.getPassword();
//        this.location = userRegistry.getLocation();
//        this.time = userRegistry.getTime();
//    }
//
//    public UserEntity2(UserInfoModel userRegistry) {
//        this.email = userRegistry.getEmail();
//        this.fname = userRegistry.getFname();
//        this.lname = userRegistry.getLname();
//        this.login = userRegistry.getLogin();
//        this.password = userRegistry.getPassword();
//        this.location = userRegistry.getLocation();
//        this.time = userRegistry.getTime();
//        this.activationCode = userRegistry.getActivationCode();
//    }
}
