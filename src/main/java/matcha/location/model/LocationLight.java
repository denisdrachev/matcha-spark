package matcha.location.model;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationLight implements Serializable {

    @Expose
    @NotNull(message = "Поле location.x не может быть пустым")
    protected Double x;

    @Expose
    @NotNull(message = "Поле location.y не может быть пустым")
    protected Double y;
}
