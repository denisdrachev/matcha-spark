package matcha.chat.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.chat.model.ChatMessage;
import matcha.chat.model.ChatMessageSave;
import matcha.db.crud.Insert;
import matcha.db.crud.Select;
import matcha.db.crud.Update;
import matcha.exception.db.*;
import matcha.model.rowMapper.ChatMessageRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO можно логирование переделать под AOP
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatDB {

    private final JdbcTemplate jdbcTemplate;

    public void insertChatMessage(ChatMessageSave message) {
        log.info("Insert chat message '{}'", message);
        try {
            int insert = jdbcTemplate.update(Insert.insertChatMessage,
                    message.getToLogin(), message.getFromLogin(), message.getMessage(), message.getTime(), false);
            log.info("Insert chat message result: {}", insert);
        } catch (Exception e) {
            log.warn("Exception. insertChatMessage: {}", e.getMessage());
            throw new InsertChatMessageDBException();
        }
    }

    public void updateChatMessage(ChatMessage message) {
        log.info("Update chat message '{}'", message);
        try {
            int update = jdbcTemplate.update(Update.updateChatMessage, true, message.getId());
            log.info("Update chat message result: {}", update);
        } catch (Exception e) {
            log.warn("Exception. updateChatMessage: {}", e.getMessage());
            throw new UpdateChatMessageDBException();
        }
    }

    public List<ChatMessage> getChatMessages(String toLogin, String fromLogin, int limit) {
        log.info("Get chat messages toLogin:{} fromLogin:{} limit:{}", toLogin, fromLogin, limit);
        try {
            List<ChatMessage> result = jdbcTemplate.query(Select.selectChatMessages, new ChatMessageRowMapper(),
                    toLogin, fromLogin, limit);
            log.info("Get chat messages. Count: {}", result.size());
            return result;
        } catch (Exception e) {
            log.warn("Failed to load chat messages. Exception message: {}", e.getMessage());
            //TODO на уровень выше поймать эксепшн и вернуть List.of();
//            return List.of();
            throw new GetChatMessageDBException();
        }
    }

    public List<ChatMessage> getFullChatMessages(String toLogin, String fromLogin, int limit) {
        log.info("Get full chat messages toLogin:{} fromLogin:{} limit:{}", toLogin, fromLogin, limit);
        try {
            List<ChatMessage> result = jdbcTemplate.query(Select.selectFullChatMessages, new ChatMessageRowMapper(),
                    toLogin, fromLogin, toLogin, fromLogin, limit);
            log.info("Get full chat messages. Count: {}", result.size());
            return result;
        } catch (Exception e) {
            log.warn("Failed to load full chat messages. Exception message: {}", e.getMessage());
            //TODO на уровень выше поймать эксепшн и вернуть List.of();
//            return List.of();
            throw new GetFullChatMessagesDBException();
        }
    }

    public List<ChatMessage> getNewChatMessages(String toLogin, String fromLogin) {
        log.info("Get new chat messages toLogin:{} fromLogin:{}", toLogin, fromLogin);
        try {
            List<ChatMessage> result = jdbcTemplate.query(Select.selectNewChatMessages, new ChatMessageRowMapper(),
                    toLogin, fromLogin);
            log.info("Get new chat messages. Count: {}", result.size());
            return result;
        } catch (Exception e) {
            log.warn("Failed to load new chat messages. Exception message: {}", e.getMessage());
            //TODO на уровень выше поймать эксепшн и вернуть List.of();
//            return List.of();
            throw new GetNewChatMessagesDBException();
        }
    }

    public List<ChatMessage> getAllNewChatMessages(String toLogin) {
        log.info("Get all new chat messages toLogin: {}", toLogin);
        try {
            List<ChatMessage> result = jdbcTemplate.query(Select.selectCountAllNewChatMessages, new ChatMessageRowMapper(),
                    toLogin);
            log.info("Get all new chat messages. Count: {}", result.size());
            return result;
        } catch (Exception e) {
            log.warn("Failed to load all new chat messages. Exception message: {}", e.getMessage());
            //TODO на уровень выше поймать эксепшн и вернуть List.of();
//            return List.of();
            throw new GetAllNewChatMessagesDBException();
        }
    }
}
