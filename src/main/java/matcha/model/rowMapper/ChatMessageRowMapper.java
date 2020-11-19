package matcha.model.rowMapper;

import matcha.chat.model.ChatMessage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatMessageRowMapper implements RowMapper<ChatMessage> {

    @Override
    public ChatMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
        ChatMessage message = new ChatMessage();
        message.setId(rs.getInt("id"));
        message.setToLogin(rs.getString("toLogin"));
        message.setFromLogin(rs.getString("fromLogin"));
        message.setMessage(rs.getString("message"));
        message.setTime(rs.getTimestamp("time"));
        message.setRead(rs.getBoolean("read"));
        return message;
    }
}