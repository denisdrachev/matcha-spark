package matcha.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventWithUserInfo {
    private String type;
    private String login;
    private Date time;
    private boolean active;
    private String fname;
    private String lname;
}
