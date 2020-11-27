//package matcha.model.rowMapper;
//
//import matcha.image.model.Image;
//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class ImageRowMapper implements RowMapper<Image> {
//
//    @Override
//    public Image mapRow(ResultSet rs, int rowNum) throws SQLException {
//        Image image = new Image();
//        image.setId(rs.getInt("id"));
//        image.setIndex(rs.getInt("index"));
//        image.setSrc(rs.getString("src"));
//        image.setAvatar(rs.getBoolean("avatar"));
//        image.setProfileId(rs.getInt("profileId"));
//        return image;
//    }
//}