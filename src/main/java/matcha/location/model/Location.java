package matcha.location.model;

import com.google.gson.annotations.Expose;
import lombok.*;
import matcha.converter.Converter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location implements Serializable {

    private int id;
    private int profileId;
    @Expose
    @NotNull(message = "Поле location.x не может быть пустым")
    private Double x;
    @Expose
    @NotNull(message = "Поле location.y не может быть пустым")
    private Double y;
    @ToString.Exclude
    private boolean active = false;
    @ToString.Exclude
    private boolean userSet = false;
    private Date time = Calendar.getInstance().getTime();

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public void setProfileId(Object user) {
        this.profileId = (int) user;
    }

    @Override
    public String toString() {
        return Converter.objectToJson(this).get();
    }
}
