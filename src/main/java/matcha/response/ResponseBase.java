package matcha.response;

import matcha.model.MyObject;
import org.json.JSONObject;

public class ResponseBase implements MyObject, Response {

    public String toString() {
        return new JSONObject(this).toString();
   }
}
