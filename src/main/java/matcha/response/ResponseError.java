package matcha.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    }
}
