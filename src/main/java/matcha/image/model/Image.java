package matcha.image.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    private int id;
    private int index;
    private String src;
    private int profileId;
    private boolean avatar;

    public Image(int index, String src, int profileId, boolean avatar) {
        this.index = index;
        this.src = src;
        this.profileId = profileId;
        this.avatar = avatar;
    }
}
