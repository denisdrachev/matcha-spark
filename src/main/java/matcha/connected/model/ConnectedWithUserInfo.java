package matcha.connected.model;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import matcha.model.MyObject;

@Data
@AllArgsConstructor
public class ConnectedWithUserInfo implements MyObject {

    @Expose
    private String login;
    @Expose
    private String fname;
    @Expose
    private String lname;
    @Expose
    private String src;
}
