package matcha.connected.db;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.Sql2oModel;
import matcha.connected.model.ConnectedEntity;
import matcha.connected.model.ConnectedWithUserInfo;
import matcha.db.crud.Insert;
import matcha.db.crud.Select;
import matcha.db.crud.Update;
import matcha.exception.db.InsertBlackListMessageDBException;
import matcha.exception.db.NotFoundBlackListMessageDBException;
import matcha.exception.db.UpdateBlackListMessageDBException;
import org.sql2o.Sql2o;

import java.util.List;

@Slf4j
//@Service
//@RequiredArgsConstructor
//@NoArgsConstructor
public class ConnectedDB {

    private final Sql2o sql2o = Sql2oModel.getSql2o();

    public void insertConnected(ConnectedEntity connectedEntity) {
        log.info("Insert connected entity '{}'", connectedEntity);
        try (org.sql2o.Connection conn = sql2o.open()) {

            int result = conn.createQuery(Insert.insertBlacklist)
                    .addParameter("fromLogin", connectedEntity.getFromLogin())
                    .addParameter("toLogin", connectedEntity.getToLogin())
                    .addParameter("isConnected", connectedEntity.isConnected())
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Insert connected entity result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. insertConnected: {}", e.getMessage());
            throw new InsertBlackListMessageDBException();
        }
    }

    public void updateConnected(ConnectedEntity connectedEntity) {
        log.info("Update connected '{}'", connectedEntity);
        try (org.sql2o.Connection conn = sql2o.open()) {

            int result = conn.createQuery(Update.updateConnected)
                    .addParameter("fromLogin", connectedEntity.getFromLogin())
                    .addParameter("fromLogin", connectedEntity.getToLogin())
                    .addParameter("toLogin", connectedEntity.getToLogin())
                    .addParameter("toLogin", connectedEntity.getFromLogin())
                    .addParameter("isConnected", connectedEntity.isConnected())
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Update connected result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. updateConnected: {}", e.getMessage());
            throw new UpdateBlackListMessageDBException();
        }
    }

    public ConnectedEntity getConnected(String fromLogin, String toLogin) {
        log.info("Get connected: [fromLogin:{}][toLogin:{}]", fromLogin, toLogin);
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<ConnectedEntity> result = conn.createQuery(Select.selectConnectedElement)
                    .addParameter("fromLogin", fromLogin)
                    .addParameter("fromLogin2", toLogin)
                    .addParameter("toLogin", toLogin)
                    .addParameter("toLogin2", fromLogin)
                    .executeAndFetch(ConnectedEntity.class);
            conn.commit();
            log.info("Get connected result: {}", result);
            return result.size() > 0 ? result.get(0) : null;
        } catch (Exception e) {
            log.warn("Failed to load connected. Exception message: {}", e.getMessage());
            throw new NotFoundBlackListMessageDBException();
        }
    }
//
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
//
    public List<ConnectedEntity> getAllConnected() {
        log.info("Get all Connected");
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<ConnectedEntity> result = conn.createQuery(Select.selectConnectedList)
                    .executeAndFetch(ConnectedEntity.class);
            conn.commit();

//            BlackListMessage result = jdbcTemplate.queryForObject(Select.selectBlacklist,
//                    new BlackListMessageRowMapper(), fromLogin, toLogin);
            log.info("Get all Connected result size: {}", result.size());
            return result;
        } catch (Exception e) {
            log.warn("Failed to load all Connected. Exception message: {}", e.getMessage());
            throw new NotFoundBlackListMessageDBException();
        }
    }
//
    public List<ConnectedWithUserInfo> getAllConnectedWithUser(String login) {
        log.info("Get all Connected with user: {}", login);
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<ConnectedWithUserInfo> result = conn.createQuery(Select.selectConnectedWithUser)
                    .addParameter("login", login)
                    .executeAndFetch(ConnectedWithUserInfo.class);
            conn.commit();
            log.info("Get all Connected users result size: {}", result.size());
            return result;
        } catch (Exception e) {
            log.warn("Failed to load all Connected users. Exception message: {}", e.getMessage());
            throw new NotFoundBlackListMessageDBException();
        }
    }
}
