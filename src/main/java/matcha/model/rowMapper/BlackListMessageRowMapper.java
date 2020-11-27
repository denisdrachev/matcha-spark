//package matcha.model.rowMapper;
//
//import matcha.blacklist.model.BlackListMessage;
//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class BlackListMessageRowMapper implements RowMapper<BlackListMessage> {
//
//    @Override
//    public BlackListMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
//        BlackListMessage message = new BlackListMessage();
//        message.setToLogin(rs.getString("toLogin"));
//        message.setFromLogin(rs.getString("fromLogin"));
//        message.setBlocked(rs.getBoolean("isBlocked"));
//        return message;
//    }
//}