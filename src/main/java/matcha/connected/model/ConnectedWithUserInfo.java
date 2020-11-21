package matcha.connected.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import matcha.model.MyObject;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectedWithUserInfo implements MyObject {

    private String login;
    private String fname;
    private String lname;
}
