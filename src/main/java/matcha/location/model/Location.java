package matcha.location.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"id", "profileId", "active", "userSet", "time"})
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

    public Location(@NotNull(message = "Поле location.x не может быть пустым") Double x,
                    @NotNull(message = "Поле location.y не может быть пустым") Double y) {
        this.x = x;
        this.y = y;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public void setProfileId(Object user) {
        this.profileId = (int) user;
    }

//    @Override
//    public String toString() {
//        return Converter.objectToJson(this).get();
//    }
}
