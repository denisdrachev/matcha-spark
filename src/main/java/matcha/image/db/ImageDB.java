package matcha.image.db;

import lombok.extern.slf4j.Slf4j;
import matcha.Sql2oModel;
import matcha.db.crud.Delete;
import matcha.db.crud.Insert;
import matcha.db.crud.Select;
import matcha.db.crud.Update;
import matcha.exception.context.image.LoadImageException;
import matcha.exception.db.image.*;
import matcha.image.model.Image;
import org.sql2o.Sql2o;

import java.util.List;

@Slf4j
//@Service
//@RequiredArgsConstructor
public class ImageDB {

//    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private final Sql2o sql2o = Sql2oModel.getSql2o();

    public Integer getImageCountById(int imageId) {
        log.info("Get image count by id: {}", imageId);
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {

            List<Integer> imagesCount = conn.createQuery(Select.selectImagesCountById)
                    .addParameter("id", imageId)
                    .executeAndFetch(Integer.class);
            conn.commit();

//            Integer imagesCount = jdbcTemplate.queryForObject(Select.selectImagesCountById,
//                    Integer.class, imageId);
            log.info("Get image count by id {} result: {}", imageId, imagesCount.get(0));
            return imagesCount.get(0);
        } catch (Exception e) {
            log.warn("Exception. getImageCountById: {}", e.getMessage());
            throw new GetImageCountByIdDBException();
        }
    }

    public Image getImageById(String imageId) {
        log.info("Get image by id: {}", imageId);
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {

            List<Image> image = conn.createQuery(Select.selectImagesCountById)
                    .addParameter("id", imageId)
                    .executeAndFetch(Image.class);
            conn.commit();

//            Image image = jdbcTemplate.queryForObject(Select.selectImageById, new ImageRowMapper(), imageId);
            log.info("Get image by id {} result: {}", imageId, image.get(0));
            return image.get(0);
        } catch (Exception e) {
            log.warn("Exception. getImageById: {}", e.getMessage());
            throw new GetImageByIdDBException();
        }
    }

    public Integer insertImage(Image image) {
        log.info("Insert image: {}", image);
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {

            Integer insert = conn.createQuery(Insert.insertImage)
                    .addParameter("index", image.getIndex())
                    .addParameter("src", image.getSrc())
                    .addParameter("profileId", image.getProfileId())
                    .addParameter("avatar", image.isAvatar())
                    .executeUpdate().getKey(Integer.class);
            conn.commit();

//            KeyHolder keyHolder = new GeneratedKeyHolder();
//            jdbcTemplate.update(new PreparedStatementCreator() {
//                @Override
//                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                    PreparedStatement ps = connection.prepareStatement(Insert.insertImage, new String[]{"id"});
//                    ps.setInt(1, image.getIndex());
//                    ps.setString(2, image.getSrc());
//                    ps.setInt(3, image.getProfileId());
//                    ps.setBoolean(4, image.isAvatar());
//                    return ps;
//                }
//            }, keyHolder);
            log.info("Insert image result: {}", insert);
            return insert;
        } catch (Exception e) {
            log.warn("Exception. insertImage: {}", e.getMessage());
            throw new InsertImageDBException();
        }
    }

    public void updateImageById(Image image) {
        log.info("Update image by id: {}", image);
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {
            System.err.println(image);
            int result = conn.createQuery(Update.updateImageById)
                    .addParameter("avatar", image.isAvatar())
                    .addParameter("src", image.getSrc())
                    .addParameter("id", image.getId())
                    .executeUpdate().getResult();
            conn.commit();
//            int update = jdbcTemplate.update(Update.updateImageById, image.getSrc(), image.isAvatar(), image.getId());
            log.info("Update image by id result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. updateImageById: {}", e.getMessage());
            throw new UpdateImageByIdDBException();
        }
    }

    public void dropImageById(String id) {
        log.info("Drop image by id: {}", id);
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {
            int result = conn.createQuery(Delete.deleteImageById)
                    .addParameter("id", id)
                    .executeUpdate().getResult();
//            int drop = jdbcTemplate.update(Drop.deleteImageById, id);
            log.info("Drop image by id result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. dropImageById: {}", e.getMessage());
            throw new DropImageByIdDBException();
        }
    }

    public List<Image> getImagesByProfileId(int profileId) {
        log.info("Get images by profile id: {}", profileId);
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {
            List<Image> images = conn.createQuery(Select.selectImageByProfileId)
                    .addParameter("profileId", profileId)
                    .executeAndFetch(Image.class);
            conn.commit();
//            List<Image> images = jdbcTemplate.query(Select.selectImageByProfileId, new ImageRowMapper(), profileId);
            log.info("Get images by id profile size: {}", images.size());
            return images;
        } catch (Exception e) {
            log.warn("Exception. getImagesByProfileId: {}", e.getMessage());
            throw new LoadImageException();
        }
    }

    public List<Image> getAllImages() {
        log.info("Get all images");
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {
            List<Image> images = conn.createQuery(Select.selectImages)
                    .executeAndFetch(Image.class);
            conn.commit();
//            List<Image> images = jdbcTemplate.query(Select.selectImages, new ImageRowMapper());
            log.info("Get all images count: {}", images.size());
            return images;
        } catch (Exception e) {
            log.warn("Exception. getAllImages: {}", e.getMessage());
            throw new LoadImageException();
        }
    }
}
