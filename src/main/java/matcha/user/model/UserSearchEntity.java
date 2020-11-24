package matcha.user.model;

import lombok.Data;
import matcha.location.model.Location;
import matcha.model.MyObject;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
public class UserSearchEntity implements Serializable, MyObject {

    private String login;
    private String fname;
    private String lname;
    private Date time = Calendar.getInstance().getTime();
    private Location location;
    private Integer glory;
    private List<String> commonTags;
}
