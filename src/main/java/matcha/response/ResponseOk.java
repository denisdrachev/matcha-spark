package matcha.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

@Data
@RequiredArgsConstructor
public class ResponseOk  extends ResponseBase {

    private String type = "ok";
    private final String token;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
