package matcha.image.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Deprecated
public class ImageEntity {

    private int id;
    private int index;
    private String src;

    public ImageEntity(int index, String src) {
        this.index = index;
        this.src = src;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"index\":")
                .append(index)
                .append(", \"src\":\"")
                .append(src)
                .append("\"}");
        return stringBuilder.toString();
    }
}
