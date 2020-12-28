package matcha.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.json.JSONObject;

@AllArgsConstructor
@Data
public class ResponseOnlyType extends ResponseBase {

    private String type;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
