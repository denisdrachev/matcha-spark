//package matcha.model.rowMapper;
//
//import matcha.user.model.UserEntity;
//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class UserRowMapper implements RowMapper<UserEntity> {
//    @Override
//    public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
//        UserEntity user = new UserEntity();
//        user.setId(rs.getInt("id"));
//        user.setFname(rs.getString("fname"));
//        user.setLname(rs.getString("lname"));
//        user.setActive(rs.getBoolean("active"));
//        user.setActivationCode(rs.getString("activationCode"));
//        user.setBlocked(rs.getBoolean("blocked"));
//        user.setEmail(rs.getString("email"));
//        user.setLogin(rs.getString("login"));
//        user.setPasswordBytes(rs.getBytes("password"));
//        user.setTime(rs.getTimestamp("time"));
//        user.setSalt(rs.getBytes("salt"));
//        user.setProfileId(rs.getInt("profileId") == 0 ? null : rs.getInt("profileId"));
//        return user;
//    }
//}