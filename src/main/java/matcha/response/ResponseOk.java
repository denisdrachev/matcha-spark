package matcha.response;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import matcha.converter.Converter;
import matcha.model.MyObject;
import org.json.JSONObject;

@Data
@RequiredArgsConstructor
public class ResponseOk  extends ResponseBase {

    private String type = "ok";
    private final String token;
//    private final String login;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
//        return Converter.objectToJson(this).get();
    }
}
