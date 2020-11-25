package matcha.chat.model;

import com.google.gson.annotations.Expose;
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
@Expose
    private String toLogin;
    @Expose
    private String fromLogin;
    @Expose
    private String message;
    @Expose
    private Date time = Calendar.getInstance().getTime();
    @Expose
    private boolean read;

    public ChatMessage(String toLogin, String fromLogin, String message) {
        this.toLogin = toLogin;
        this.fromLogin = fromLogin;
        this.message = message;
        this.read = false;
    }
}
