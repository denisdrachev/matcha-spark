package matcha.image.db;

import lombok.extern.slf4j.Slf4j;
import matcha.Sql2oModel;
import matcha.db.crud.Delete;
import matcha.db.crud.Insert;
import matcha.db.crud.Select;
import matcha.db.crud.Update;
import matcha.exception.db.DeleteDBException;
import matcha.exception.db.InsertDBException;
import matcha.exception.db.SelectDBException;
import matcha.exception.db.UpdateDBException;
import matcha.image.model.Image;
import org.sql2o.Sql2o;

import java.util.List;

@Slf4j
public class ImageDB {

    private final Sql2o sql2o = Sql2oModel.getSql2o();

    public Integer getImageCountById(int imageId) {
        log.info("Get image count by id: {}", imageId);
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<Integer> imagesCount = conn.createQuery(Select.selectImagesCountById)
                    .addParameter("id", imageId)
                    .executeAndFetch(Integer.class);
            conn.commit();
            log.info("Get image count by id {} result: {}", imageId, imagesCount.get(0));
            return imagesCount.get(0);
        } catch (Exception e) {
            log.warn("Exception. getImageCountById: {}", e.getMessage());
            throw new SelectDBException();
        }
    }

    public Image getImageById(String imageId) {
        log.info("Get image by id: {}", imageId);
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<Image> image = conn.createQuery(Select.selectImagesCountById)
                    .addParameter("id", imageId)
                    .executeAndFetch(Image.class);
            conn.commit();
            log.info("Get image by id {} result: {}", imageId, image.get(0));
            return image.get(0);
        } catch (Exception e) {
            log.warn("Exception. getImageById: {}", e.getMessage());
            throw new SelectDBException();
        }
    }

    public Integer insertImage(Image image) {
        log.info("Insert image: {}", image);
        try (org.sql2o.Connection conn = sql2o.open()) {

            Integer insert = conn.createQuery(Insert.insertImage)
                    .addParameter("index", image.getIndex())
                    .addParameter("src", image.getSrc())
                    .addParameter("profileId", image.getProfileId())
                    .addParameter("avatar", image.isAvatar())
                    .executeUpdate().getKey(Integer.class);
            conn.commit();
            log.info("Insert image result: {}", insert);
            return insert;
        } catch (Exception e) {
            log.warn("Exception. insertImage: {}", e.getMessage());
            throw new InsertDBException();
        }
    }

    public void updateImageById(Image image) {
        log.info("Update image by id: {}", image);
        try (org.sql2o.Connection conn = sql2o.open()) {
            int result = conn.createQuery(Update.updateImageById)
                    .addParameter("avatar", image.isAvatar())
                    .addParameter("src", image.getSrc())
                    .addParameter("id", image.getId())
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Update image by id result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. updateImageById: {}", e.getMessage());
            throw new UpdateDBException();
        }
    }

    public void updateClearAvatarByProfileId(int profileId) {
        log.info("Update clear avatar by profileId: {}", profileId);
        try (org.sql2o.Connection conn = sql2o.open()) {
            int result = conn.createQuery(Update.updateClearAvatarByProfileId)
                    .addParameter("profileId", profileId)
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Update clear avatar by profileId. Result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. updateClearAvatarByProfileId: {}", e.getMessage());
            throw new UpdateDBException();
        }
    }

    public void dropImageById(String id) {
        log.info("Drop image by id: {}", id);
        try (org.sql2o.Connection conn = sql2o.open()) {
            int result = conn.createQuery(Delete.deleteImageById)
                    .addParameter("id", id)
                    .executeUpdate().getResult();
            log.info("Drop image by id result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. dropImageById: {}", e.getMessage());
            throw new DeleteDBException();
        }
    }

    public List<Image> getImagesByProfileId(int profileId) {
        log.info("Get images by profile id: {}", profileId);
        try (org.sql2o.Connection conn = sql2o.open()) {
            List<Image> images = conn.createQuery(Select.selectImageByProfileId)
                    .addParameter("profileId", profileId)
                    .executeAndFetch(Image.class);
            conn.commit();
            log.info("Get images by id profile size: {}", images.size());
            return images;
        } catch (Exception e) {
            log.warn("Exception. getImagesByProfileId: {}", e.getMessage());
            throw new SelectDBException();
        }
    }

    public List<Image> getAllImages() {
        log.info("Get all images");
        try (org.sql2o.Connection conn = sql2o.open()) {
            List<Image> images = conn.createQuery(Select.selectImages)
                    .executeAndFetch(Image.class);
            conn.commit();
            log.info("Get all images count: {}", images.size());
            return images;
        } catch (Exception e) {
            log.warn("Exception. getAllImages: {}", e.getMessage());
            throw new SelectDBException();
        }
    }
}
