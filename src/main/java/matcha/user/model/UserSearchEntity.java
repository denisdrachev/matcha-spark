package matcha.user.model;

import com.google.gson.annotations.Expose;
import lombok.Data;
import matcha.model.MyObject;

import java.io.Serializable;

@Data
public class UserSearchEntity implements Serializable, MyObject {
    //"SELECT u.login, u.fname, u.lname, l.x, l.y, i.src, r.rating
    @Expose
    private String login;
    @Expose
    private String fname;
    @Expose
    private String lname;
    //    private Date time = Calendar.getInstance().getTime();
    @Expose
    private Double x;
    @Expose
    private Double y;
    @Expose
    private Integer rating;
    @Expose
    private Integer tagsCount;
    @Expose
    private String src;
}
