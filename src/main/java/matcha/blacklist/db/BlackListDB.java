package matcha.blacklist.db;

import lombok.extern.slf4j.Slf4j;
import matcha.Sql2oModel;
import matcha.blacklist.model.BlackListMessage;
import matcha.db.crud.Insert;
import matcha.db.crud.Select;
import matcha.db.crud.Update;
import matcha.exception.db.InsertDBException;
import matcha.exception.db.SelectDBException;
import matcha.exception.db.UpdateDBException;
import org.sql2o.Sql2o;

import java.util.List;

@Slf4j
public class BlackListDB {

    private final Sql2o sql2o = Sql2oModel.getSql2o();

    public void insertBlackListMessage(BlackListMessage message) {
        log.info("Insert BlackList message '{}'", message);
        try (org.sql2o.Connection conn = sql2o.open()) {

            int result = conn.createQuery(Insert.insertBlacklist)
                    .addParameter("fromLogin", message.getFromLogin())
                    .addParameter("toLogin", message.getToLogin())
                    .addParameter("isBlocked", message.isBlocked())
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Insert BlackList message result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. insertBlackListMessage: {}", e.getMessage());
            throw new InsertDBException();
        }
    }

    public void updateBlackListMessage(BlackListMessage message) {
        log.info("Update BlackList message '{}'", message);
        try (org.sql2o.Connection conn = sql2o.open()) {

            int result = conn.createQuery(Update.updateBlacklistById)
                    .addParameter("fromLogin", message.getFromLogin())
                    .addParameter("toLogin", message.getToLogin())
                    .addParameter("isBlocked", message.isBlocked())
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Update BlackList message result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. updateBlackListMessage: {}", e.getMessage());
            throw new UpdateDBException();
        }
    }

    public BlackListMessage getBlackListMessage(String fromLogin, String toLogin) {
        log.info("Get BlackList message: [fromLogin:{}][toLogin:{}]", fromLogin, toLogin);
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<BlackListMessage> result = conn.createQuery(Select.selectBlacklist)
                    .addParameter("fromLogin", fromLogin)
                    .addParameter("toLogin", toLogin)
                    .executeAndFetch(BlackListMessage.class);
            conn.commit();
            log.info("Get BlackList message result: {}", result);
            return result.get(0);
        } catch (Exception e) {
            log.warn("Failed to load BlackList message. Exception message: {}", e.getMessage());
            throw new SelectDBException();
        }
    }

    public Boolean isBlackListExists(String fromLogin, String toLogin) {
        log.info("Get BlackList message: [fromLogin:{}][toLogin:{}]", fromLogin, toLogin);
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<Integer> integer = conn.createQuery(Select.selectBlackListCount)
                    .addParameter("fromLogin", fromLogin)
                    .addParameter("toLogin", toLogin)
                    .executeAndFetch(Integer.class);
            conn.commit();
            log.info("Get BlackList message result: {}", integer);
            return integer.get(0) > 0;
        } catch (Exception e) {
            log.warn("Exception. isBlackListExists: {}", e.getMessage());
            return false;
        }
    }

    public List<BlackListMessage> getAllBlackList() {
        log.info("Get all BlackList");
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<BlackListMessage> result = conn.createQuery(Select.selectBlacklists)
                    .executeAndFetch(BlackListMessage.class);
            conn.commit();
            log.info("Get all BlackList result size: {}", result.size());
            return result;
        } catch (Exception e) {
            log.warn("Failed to load all BlackList messages. Exception message: {}", e.getMessage());
            throw new SelectDBException();
        }
    }
}
