package matcha.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    private long id;
    private String toLogin;
    private String fromLogin;
    private String message;
    private Date time = Calendar.getInstance().getTime();
    private boolean read;

    public ChatMessage(String toLogin, String fromLogin, String message) {
        this.toLogin = toLogin;
        this.fromLogin = fromLogin;
        this.message = message;
        this.read = false;
    }
}
