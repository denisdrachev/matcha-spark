package matcha.rating.manipulation;

import lombok.extern.slf4j.Slf4j;
import matcha.rating.db.RatingDB;
import matcha.rating.model.Rating;

import java.util.List;

@Slf4j
public class RatingManipulator {

    private final RatingDB ratingDB = new RatingDB();

    public void insertRating(Rating rating) {
        ratingDB.insertRating(rating);
    }

    public void incRatingByLogin(String login) {
        ratingDB.incRatingByLogin(login);
    }

//    public void updateBlackListMessage(BlackListMessage blackListMessage) {
//        ratingDB.updateBlackListMessage(blackListMessage);
//    }

    public Rating getRatingByLogin(String login) {
        return ratingDB.getRatingByLogin(login);
    }

//    public boolean isBlackListExists(String fromLogin, String toLogin) {
//        return ratingDB.isBlackListExists(fromLogin, toLogin);
//    }

    public List<Rating> getAllRatings() {
        return ratingDB.getAllRatings();
    }

    public void decRatingByLogin(String login) {
        ratingDB.decRatingByLogin(login);
    }
}
