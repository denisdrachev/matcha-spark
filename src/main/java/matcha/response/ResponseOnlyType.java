package matcha.response;

import lombok.*;
import matcha.converter.Converter;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseOnlyType implements Response {

    private String type;

//    @Override
//    public String toString() {
//        return Converter.objectToJson(this).get();
//    }
}
