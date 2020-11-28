package matcha.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import matcha.model.MyObject;
import org.json.JSONObject;

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

    //    @Override
//    public String toString() {
//        return Converter.objectToJson(this).get();
//    }
}
