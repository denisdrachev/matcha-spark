package matcha.user.model;

import lombok.*;
import matcha.location.model.Location;
import matcha.model.MyObject;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@ToString()
@AllArgsConstructor
@NoArgsConstructor
public class UserEntityFull implements Serializable, MyObject {

    private int id;
    private String login;
    private String password;
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

    public UserEntityFull(UserRegistry userRegistry) {
        this.email = userRegistry.getEmail();
        this.fname = userRegistry.getFname();
        this.lname = userRegistry.getLname();
        this.login = userRegistry.getLogin();
        this.password = userRegistry.getPassword();
        this.location = new Location(userRegistry.getLocation());
        this.time = Calendar.getInstance().getTime();
    }

    public UserEntityFull(UserEntity userRegistry) {
        this.email = userRegistry.getEmail();
        this.fname = userRegistry.getFname();
        this.lname = userRegistry.getLname();
        this.login = userRegistry.getLogin();
        this.password = userRegistry.getPassword();
        this.passwordBytes = userRegistry.getPasswordBytes();
        this.location = userRegistry.getLocation();
        this.time = userRegistry.getTime();
        this.activationCode = userRegistry.getActivationCode();
        this.profileId = userRegistry.getProfileId();
        this.id = userRegistry.getId();
        this.salt = userRegistry.getSalt();
        this.active = userRegistry.isActive();
        this.blocked = userRegistry.isBlocked();
    }
}
