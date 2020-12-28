package matcha.rating.db;

import lombok.extern.slf4j.Slf4j;
import matcha.Sql2oModel;
import matcha.db.crud.Insert;
import matcha.db.crud.Select;
import matcha.db.crud.Update;
import matcha.exception.db.InsertDBException;
import matcha.exception.db.SelectDBException;
import matcha.exception.db.UpdateDBException;
import matcha.rating.model.Rating;
import org.sql2o.Sql2o;

import java.util.List;

@Slf4j
public class RatingDB {

    private final Sql2o sql2o = Sql2oModel.getSql2o();

    public void insertRating(Rating rating) {
        log.info("Insert Rating '{}'", rating);
        try (org.sql2o.Connection conn = sql2o.open()) {

            int result = conn.createQuery(Insert.insertRating)
                    .addParameter("rating", rating.getRating())
                    .addParameter("login", rating.getLogin())
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Insert Rating. Result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. insertRating: {}", e.getMessage());
            throw new InsertDBException();
        }
    }

    public void incRatingByLogin(String login) {
        log.info("Inc Rating by login: {}", login);
        try (org.sql2o.Connection conn = sql2o.open()) {
            int result = conn.createQuery(Update.updateIncRatingByLogin)
                    .addParameter("login", login)
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Inc Rating by login. Result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. incRatingByLogin: {}", e.getMessage());
            throw new UpdateDBException();
        }
    }

    public Rating getRatingByLogin(String login) {
        log.info("Get Rating by login. {}", login);
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<Rating> result = conn.createQuery(Select.selectRatingByLogin)
                    .addParameter("login", login)
                    .executeAndFetch(Rating.class);
            conn.commit();
            log.info("Get Rating by login. Result: {}", result);
            return result.get(0);
        } catch (Exception e) {
            log.warn("Exception. getRatingByLogin: {}", e.getMessage());
            throw new SelectDBException();
        }
    }

    public List<Rating> getAllRatings() {
        log.info("Get all Ratings");
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<Rating> result = conn.createQuery(Select.selectRating)
                    .executeAndFetch(Rating.class);
            conn.commit();
            log.info("Get all Ratings. Result size: {}", result.size());
            return result;
        } catch (Exception e) {
            log.warn("Exception. getAllRatings: {}", e.getMessage());
            throw new SelectDBException();
        }
    }

    public void decRatingByLogin(String login) {
        log.info("Dec Rating by login: {}", login);
        try (org.sql2o.Connection conn = sql2o.open()) {
            int result = conn.createQuery(Update.updateDecRatingByLogin)
                    .addParameter("login", login)
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Dec Rating by login. Result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. decRatingByLogin: {}", e.getMessage());
            throw new UpdateDBException();
        }
    }
}
