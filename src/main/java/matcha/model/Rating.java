package matcha.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Rating implements Serializable {

    private int id;
    private int profile;
    private int rating;
}
