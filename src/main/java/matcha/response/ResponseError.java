package matcha.response;

import lombok.*;
import matcha.converter.Converter;
import matcha.model.MyObject;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseError implements MyObject, Response {
    private String type;
    private String message;

//    @Override
//    public String toString() {
//        return Converter.objectToJson(this).get();
//    }
}
