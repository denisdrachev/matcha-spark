package matcha.connected.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import matcha.model.MyObject;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectedEntity implements MyObject {

    @NotBlank(message = "Поле 'toLogin' не может быть пустым")
    private String toLogin;
    private String fromLogin;
    private boolean isConnected;
}
