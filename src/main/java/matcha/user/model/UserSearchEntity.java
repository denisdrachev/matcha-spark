package matcha.user.model;

import com.google.gson.annotations.Expose;
import lombok.Data;
import matcha.converter.Utils;
import matcha.model.MyObject;

import java.io.Serializable;

@Data
public class UserSearchEntity implements Serializable, MyObject {
    @Expose
    private String login;
    @Expose
    private String fname;
    @Expose
    private String lname;
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
    @Expose
    private Integer age;

    @Expose
    private Integer gender;
    @Expose
    private Integer preference;
    @Expose
    private String biography;
    @Expose
    private double distance;

    public void initDistance(Double x, Double y) {
        this.distance = Utils.getDistance(x, y, this.x, this.y);
    }
}
