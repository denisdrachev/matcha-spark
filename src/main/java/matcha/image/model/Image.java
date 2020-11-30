package matcha.image.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"id", "profileId"})
public class Image {

    private int id;
    @Expose
    private int index;
    @Expose
    private String src;
    private int profileId;
    @Expose
    private boolean avatar;

    public Image(int index, String src, int profileId, boolean avatar) {
        this.index = index;
        this.src = src;
        this.profileId = profileId;
        this.avatar = avatar;
    }
}
