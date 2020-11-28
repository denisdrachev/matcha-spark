package matcha.response;

import lombok.*;
import matcha.converter.Converter;
import org.json.JSONObject;

@AllArgsConstructor
//@NoArgsConstructor
@Data
public class ResponseOnlyType extends ResponseBase {

    private String type;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
//        return Converter.objectToJson(this).get();
    }
}
