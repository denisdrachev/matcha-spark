package matcha.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import matcha.model.MyObject;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageFull implements MyObject {

    @NotBlank(message = "Поле 'toLogin' не может быть пустым")
    private String toLogin;
//    @NotBlank(message = "Поле 'fromLogin' не может быть пустым")
    private String fromLogin;
    @Min(value = 1)
    @NotNull
    private Integer limit;
    private Date time = Calendar.getInstance().getTime();
}
