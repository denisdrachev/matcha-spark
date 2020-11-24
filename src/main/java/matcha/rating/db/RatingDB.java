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
        log.info("Inc Rating by login: {}", log);
        try (org.sql2o.Connection conn = sql2o.open()) {
            int result = conn.createQuery(Update.updateIncRatingByLogin)
                    .addParameter("login", login)
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Inc Rating by login. Result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. incRatingByLogin: {}", e.getMessage());
            e.printStackTrace();
            throw new UpdateDBException();
        }
    }

//    public void updateBlackListMessage(BlackListMessage message) {
//        log.info("Update BlackList message '{}'", message);
//        try (org.sql2o.Connection conn = sql2o.open()) {
//
//            int result = conn.createQuery(Update.updateBlacklistById)
//                    .addParameter("fromLogin", message.getFromLogin())
//                    .addParameter("toLogin", message.getToLogin())
//                    .addParameter("isBlocked", message.isBlocked())
//                    .executeUpdate().getResult();
//            conn.commit();
//
////            int update = jdbcTemplate.update(Update.updateBlacklistById,
////                    message.isBlocked(), message.getFromLogin(), message.getToLogin());
//            log.info("Update BlackList message result: {}", result);
//        } catch (Exception e) {
//            log.warn("Exception. updateBlackListMessage: {}", e.getMessage());
//            throw new UpdateBlackListMessageDBException();
//        }
//    }

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

//    public Boolean isBlackListExists(String fromLogin, String toLogin) {
//        log.info("Get BlackList message: [fromLogin:{}][toLogin:{}]", fromLogin, toLogin);
//        try (org.sql2o.Connection conn = sql2o.open()) {
//
//            //SELECT COUNT(*) FROM blacklist WHERE fromLogin = :fromLogin AND toLogin = :toLogin
//            List<Integer> integer = conn.createQuery(Select.selectBlackListCount)
//                    .addParameter("fromLogin", fromLogin)
//                    .addParameter("toLogin", toLogin)
//                    .executeAndFetch(Integer.class);
//            conn.commit();
//
////            Integer integer = jdbcTemplate.queryForObject(Select.selectBlackListCount,
////                    Integer.class, fromLogin, toLogin);
//            log.info("Get BlackList message result: {}", integer);
//            return integer.get(0) > 0;
//        } catch (Exception e) {
//            log.warn("Exception. isBlackListExists: {}", e.getMessage());
//            return false;
//        }
//    }

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
}
