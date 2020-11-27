package matcha.profile.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProfileEntityWithoutImages implements Serializable {

    private Integer id;
    private Integer age = null;
    private Integer gender = null;
    private Integer preference;
    private String biography;
    private String tags;
    private boolean isFilled;
}
