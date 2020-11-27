//package matcha.model.rowMapper;
//
//import matcha.event.model.Event;
//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class EventRowMapper implements RowMapper<Event> {
//
//    @Override
//    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
//        Event event = new Event();
//        event.setId(rs.getInt("id"));
//        event.setType(rs.getString("type"));
//        event.setLogin(rs.getString("login"));
//        event.setTime(rs.getTimestamp("time"));
//        event.setActive(rs.getBoolean("active"));
//        event.setData(rs.getString("data"));
//        return event;
//    }
//}