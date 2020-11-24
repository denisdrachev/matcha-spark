package matcha.rating.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import matcha.rating.manipulation.RatingManipulator;
import matcha.rating.model.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class RatingService {

    private RatingManipulator ratingManipulator = new RatingManipulator();

    private static RatingService ratingService;

    public static RatingService getInstance() {
        if (ratingService == null) {
            ratingService = new RatingService();
        }
        return ratingService;
    }

    public Rating getRatingByLogin(String login) {
        return ratingManipulator.getRatingByLogin(login);
    }

//    public boolean isBlackListExists(String fromLogin, String toLogin) {
//        return ratingManipulator.isBlackListExists(fromLogin, toLogin);
//    }

//    public void updateBlackListMessage(BlackListMessage message) {
//        ratingManipulator.updateBlackListMessage(message);
//    }

    public void createRating(String login) {
        Rating rating = new Rating();
        rating.setLogin(login);
        rating.setRating(1);
        ratingManipulator.insertRating(rating);
    }

    public void incRatingByLogin(String login) {
        ratingManipulator.incRatingByLogin(login);
    }

    public List<Rating> getAllRatings() {
        return ratingManipulator.getAllRatings();
    }
}
