package matcha.location.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@JsonIgnoreProperties(value = {"id", "profileId", "active", "userSet", "time"})
public class Location extends LocationLight implements Serializable {

    private int id;
    private int profileId;
    private boolean active = false;
    private boolean userSet = false;
    private Date time = Calendar.getInstance().getTime();

    public Location(Double x, Double y) {
        super(x, y);
    }

    public Location(LocationLight locationLight) {
        super(locationLight.getX(), locationLight.getY());
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public void setProfileId(Object user) {
        this.profileId = (int) user;
    }
}
