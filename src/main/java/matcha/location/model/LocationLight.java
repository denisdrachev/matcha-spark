package matcha.location.model;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationLight implements Serializable {

    @Expose
    @DecimalMin(value = "-180.0", message = "Допустимое значение location.x от -180 до 180")
    @DecimalMax(value = "180.0", message = "Допустимое значение location.x от -180 до 180")
    @NotNull(message = "Поле location.x не может быть пустым")
    protected Double x;

    @Expose
    @DecimalMin(value = "-180.0", message = "Допустимое значение location.y от -180 до 180")
    @DecimalMax(value = "180.0", message = "Допустимое значение location.y от -180 до 180")
    @NotNull(message = "Поле location.y не может быть пустым")
    protected Double y;
}
