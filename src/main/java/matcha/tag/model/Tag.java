package matcha.tag.model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class Tag {
    private int id;
    @Expose
    private String name;
    @Expose
    private int count;
}
