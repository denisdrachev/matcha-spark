package matcha.connected.model;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import matcha.model.MyObject;

import javax.validation.constraints.NotBlank;

@Data
//@NoArgsConstructor
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
