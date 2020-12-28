package matcha.response;

import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResponseOkDataObject  extends ResponseBase {

    private String type;
    private JsonElement data;

    @Override
    public String toString() {
        return "{\"type\":\"" + type + "\"" +
                ", \"data\":" + data +
                '}';
    }
}
