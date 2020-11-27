//package matcha.model.rowMapper;
//
//import matcha.profile.model.ProfileEntity;
//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class ProfileRowMapper implements RowMapper<ProfileEntity> {
//    @Override
//    public ProfileEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
//        ProfileEntity profile = new ProfileEntity();
//        profile.setId(rs.getInt("id"));
//        profile.setAge(rs.getInt("age") == 0 ? null : rs.getInt("age"));
//        profile.setBiography(rs.getString("biography"));
//        profile.setGender(rs.getInt("gender") == 0 ? null : rs.getInt("gender"));
////        if (rs.getString("preference") != null)
////            profile.setPreference(Stream.of(rs.getString("preference").split(","))
////                    .map(Integer::parseInt).collect(Collectors.toList()));
////        if (rs.getString("tags") != null)
////            profile.setTags(Arrays.asList(rs.getString("tags").split(",")));
//        profile.setFilled(rs.getBoolean("isFilled"));
//        return profile;
//    }
//}