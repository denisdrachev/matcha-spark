package matcha.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResponseOkData extends ResponseBase {

    private String type;
    private Object data;

    @Override
    public String toString() {
        return "{\"type\":\"" + type + "\"" +
                ", \"data\":" + data +
                '}';
    }
}
