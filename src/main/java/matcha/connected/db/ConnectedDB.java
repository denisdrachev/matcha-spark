package matcha.connected.db;

import lombok.extern.slf4j.Slf4j;
import matcha.Sql2oModel;
import matcha.connected.model.ConnectedEntity;
import matcha.connected.model.ConnectedWithUserInfo;
import matcha.db.crud.Insert;
import matcha.db.crud.Select;
import matcha.db.crud.Update;
import matcha.exception.db.InsertDBException;
import matcha.exception.db.SelectDBException;
import matcha.exception.db.UpdateDBException;
import org.sql2o.Sql2o;

import java.util.List;

@Slf4j
public class ConnectedDB {

    private final Sql2o sql2o = Sql2oModel.getSql2o();

    public void insertConnected(ConnectedEntity connectedEntity) {
        log.info("Insert connected entity '{}'", connectedEntity);
        try (org.sql2o.Connection conn = sql2o.open()) {

            int result = conn.createQuery(Insert.insertConnected)
                    .addParameter("fromLogin", connectedEntity.getFromLogin())
                    .addParameter("toLogin", connectedEntity.getToLogin())
                    .addParameter("isConnected", connectedEntity.isConnected())
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Insert connected entity result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. insertConnected: {}", e.getMessage());
            throw new InsertDBException();
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
            throw new UpdateDBException();
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
            throw new SelectDBException();
        }
    }

    public List<ConnectedEntity> getAllConnected() {
        log.info("Get all Connected");
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<ConnectedEntity> result = conn.createQuery(Select.selectConnectedList)
                    .executeAndFetch(ConnectedEntity.class);
            conn.commit();
            log.info("Get all Connected result size: {}", result.size());
            return result;
        } catch (Exception e) {
            log.warn("Failed to load all Connected. Exception message: {}", e.getMessage());
            throw new SelectDBException();
        }
    }

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
            throw new SelectDBException();
        }
    }
}
