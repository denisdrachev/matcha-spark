package matcha.chat.db;

import lombok.extern.slf4j.Slf4j;
import matcha.Sql2oModel;
import matcha.chat.model.ChatMessage;
import matcha.chat.model.ChatMessageSave;
import matcha.db.crud.Insert;
import matcha.db.crud.Select;
import matcha.db.crud.Update;
import matcha.exception.db.*;
import org.sql2o.Sql2o;

import java.util.List;

@Slf4j
public class ChatDB {

    private final Sql2o sql2o = Sql2oModel.getSql2o();

    public void insertChatMessage(ChatMessageSave message) {
        log.info("Insert chat message: {}", message);
        try (org.sql2o.Connection conn = sql2o.open()) {

            Integer result = conn.createQuery(Insert.insertChatMessage)
                    .addParameter("toLogin", message.getToLogin())
                    .addParameter("fromLogin", message.getFromLogin())
                    .addParameter("message", message.getMessage())
                    .addParameter("time", message.getTime())
                    .addParameter("read", false)
                    .executeUpdate().getResult();
            conn.commit();

            log.info("Insert chat message. Result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. insertChatMessage: {}", e.getMessage());
            throw new InsertChatMessageDBException();
        }
    }

    public void updateChatMessage(ChatMessage message) {
        log.info("Update chat message '{}'", message);
        try (org.sql2o.Connection conn = sql2o.open()) {
            int result = conn.createQuery(Update.updateChatMessage)
                    .addParameter("read", message.isRead())
                    .addParameter("id", message.getId())
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Update chat message. Result: {}", result);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception. updateChatMessage: {}", e.getMessage());
            throw new UpdateChatMessageDBException();
        }
    }

    public void updateChatMessagesByIds(List<Long> ids) {
        log.info("Update chat messages by ids '{}'", ids.size());
        try (org.sql2o.Connection conn = sql2o.open()) {
            int result = conn.createQuery(Update.updateChatMessagesByIds)
                    .addParameter("read", true)
                    .addParameter("ids", ids)
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Update chat messages by ids. Result: {}", result);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception. updateChatMessagesByIds: {}", e.getMessage());
            throw new UpdateDBException();
        }
    }

    public List<ChatMessage> getChatMessages(String toLogin, String fromLogin, int limit) {
        log.info("Get chat messages toLogin:{} fromLogin:{} limit:{}", toLogin, fromLogin, limit);
        try (org.sql2o.Connection conn = sql2o.open()) {
            List<ChatMessage> chatMessages = conn.createQuery(Select.selectChatMessages)
                    .addParameter("fromLogin", fromLogin)
                    .addParameter("toLogin", toLogin)
                    .addParameter("limit", limit)
                    .executeAndFetch(ChatMessage.class);
            conn.commit();
            log.info("Get chat messages. Result size: {}", chatMessages.size());
            return chatMessages;
        } catch (Exception e) {
            log.warn("Exception. getChatMessages: {}", e.getMessage());
            throw new GetChatMessageDBException();
        }
    }

    public List<ChatMessage> getFullChatMessages(String toLogin, String fromLogin, int limit) {
        log.info("Get full chat messages toLogin:{} fromLogin:{} limit:{}", toLogin, fromLogin, limit);
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<ChatMessage> chatMessages = conn.createQuery(Select.selectFullChatMessages)
                    .addParameter("fromLogin", fromLogin)
                    .addParameter("toLogin", toLogin)
                    .addParameter("limit", limit)
                    .executeAndFetch(ChatMessage.class);
            conn.commit();
            log.info("Get full chat messages. Result size: {}", chatMessages.size());
            return chatMessages;
        } catch (Exception e) {
            log.warn("Exception. getFullChatMessages: {}", e.getMessage());
            //TODO на уровень выше поймать эксепшн и вернуть List.of();
//            return List.of();
            throw new GetFullChatMessagesDBException();
        }
    }

    public List<ChatMessage> getNewChatMessages(String toLogin, String fromLogin) {
        log.info("Get new chat messages toLogin:{} fromLogin:{}", toLogin, fromLogin);
        try (org.sql2o.Connection conn = sql2o.open()) {
            List<ChatMessage> result = conn.createQuery(Select.selectNewChatMessages)
                    .addParameter("fromLogin", fromLogin)
                    .addParameter("toLogin", toLogin)
                    .executeAndFetch(ChatMessage.class);
            conn.commit();
            log.info("Get new chat messages. Result size: {}", result.size());
            return result;
        } catch (Exception e) {
            log.warn("Exception. getNewChatMessages: {}", e.getMessage());
            //TODO на уровень выше поймать эксепшн и вернуть List.of();
//            return List.of();
            throw new GetNewChatMessagesDBException();
        }
    }

    public List<ChatMessage> getAllNewChatMessages(String toLogin) {
        log.info("Get all new chat messages toLogin: {}", toLogin);
        try (org.sql2o.Connection conn = sql2o.open()) {
            List<ChatMessage> result = conn.createQuery(Select.selectCountAllNewChatMessages)
                    .addParameter("toLogin", toLogin)
                    .executeAndFetch(ChatMessage.class);
            conn.commit();
            log.info("Get all new chat messages. Result size: {}", result.size());
            return result;
        } catch (Exception e) {
            log.warn("Exception. getAllNewChatMessages: {}", e.getMessage());
            //TODO на уровень выше поймать эксепшн и вернуть List.of();
//            return List.of();
            throw new GetAllNewChatMessagesDBException();
        }
    }

    public List<ChatMessage> getAllMessages() {
        log.info("Get all messages");
        try (org.sql2o.Connection conn = sql2o.open()) {
            List<ChatMessage> result = conn.createQuery(Select.selectAllMessages)
                    .executeAndFetch(ChatMessage.class);
            conn.commit();
            log.info("Get all messages. Result size: {}", result.size());
            return result;
        } catch (Exception e) {
            log.warn("Exception. getAllMessages: {}", e.getMessage());
            throw new SelectDBException();
        }
    }
}
