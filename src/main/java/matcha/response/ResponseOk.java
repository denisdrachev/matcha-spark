package matcha.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import matcha.converter.Converter;
import matcha.model.MyObject;

@Setter
@Getter
@RequiredArgsConstructor
public class ResponseOk implements MyObject, Response {

    private String type = "success";
    private final String token;
//    private final String login;

    @Override
    public String toString() {
        return Converter.objectToJson(this).get();
    }
}
