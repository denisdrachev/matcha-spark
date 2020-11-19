package matcha.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import matcha.model.MyObject;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResponseDataList implements MyObject, Response {

    private String type;
    private List data;
}
