package matcha.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private int id;
    private String type;
    private String login;
    private Date time;
    @ToString.Exclude
    private boolean active = true;
    private String data;

    public Event(String type, String login, Date time, boolean active, String data) {
        this.type = type;
        this.login = login;
        this.time = time;
        this.active = active;
        this.data = data;
    }

    public Event(String type, String login, boolean active, String data) {
        this.type = type;
        this.login = login;
        this.active = active;
        this.data = data;
    }
}
