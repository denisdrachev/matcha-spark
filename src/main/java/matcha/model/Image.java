package matcha.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import matcha.converter.Converter;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Deprecated
public class Image implements Serializable {

    private int index;
    private String src;
    private boolean main;

    @Override
    public String toString() {
        return Converter.objectToJson(this).get();
    }
}
