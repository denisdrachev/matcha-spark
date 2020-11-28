package matcha.response;

import lombok.*;
import matcha.converter.Converter;
import matcha.model.MyObject;
import org.json.JSONObject;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseError extends ResponseBase{
    private String type;
    private String message;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
//        return Converter.objectToJson(this).get();
    }
}
