package matcha.event.model;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventWithUserInfo {
    @Expose
    private String type;
    @Expose
    private String login;
    @Expose
    private Date time;
    private boolean active;
    @Expose
    private String fname;
    @Expose
    private String lname;
    @Expose
    private String src;
    @Expose
    private Integer gender;
}
