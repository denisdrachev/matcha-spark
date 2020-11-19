package matcha.like.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ImageLikeModel implements Serializable {

    private int id;
    private boolean active;
    private int image;  //изменить на imageId
    private int who;
    private int whom;
}
