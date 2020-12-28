package matcha.profile.db;

import lombok.extern.slf4j.Slf4j;
import matcha.Sql2oModel;
import matcha.db.crud.Delete;
import matcha.db.crud.Insert;
import matcha.db.crud.Select;
import matcha.db.crud.Update;
import matcha.exception.context.UserRegistrationException;
import matcha.exception.db.DeleteDBException;
import matcha.exception.db.SelectDBException;
import matcha.exception.db.UpdateDBException;
import matcha.profile.model.ProfileEntity;
import matcha.profile.model.ProfileEntityWithoutImages;
import org.sql2o.Sql2o;

import java.util.List;

@Slf4j
public class ProfileDB {

    private final Sql2o sql2o = Sql2oModel.getSql2o();

    public Integer insertEmptyProfile() {
        log.info("Create empty profile");
        try (org.sql2o.Connection conn = sql2o.open()) {

            Integer profileId = conn.createQuery(Insert.insertProfile)
                    .addParameter("age", 0)
                    .addParameter("gender", 3)
                    .addParameter("preference", 3)
                    .addParameter("biography", "")
                    .addParameter("isFilled", false)
                    .executeUpdate().getKey(Integer.class);
            conn.commit();
            log.info("Create empty profile result: {}", profileId);
            return profileId;
        } catch (Exception e) {
            log.warn("Exception. insertEmptyProfile: {}", e.getMessage());
            throw new UserRegistrationException();
        }
    }

    public Integer getProfileCountById(int profileId) {
        log.info("Get profile cound by ID. profileId: {}", profileId);
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<Integer> count = conn.createQuery(Select.selectProfilesCountById)
                    .addParameter("id", profileId)
                    .executeAndFetch(Integer.class);
            conn.commit();
            log.info("Get profile cound by ID. profileId: {} count: {}", profileId, count.get(0));
            return count.get(0);
        } catch (Exception e) {
            log.warn("Exception. getProfileCountById: {}", e.getMessage());
            throw new SelectDBException();
        }
    }

    public ProfileEntity getProfileById(int profileId) {
        log.info("Get profile by ID: {}", profileId);
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<ProfileEntityWithoutImages> profile = conn.createQuery(Select.selectProfileById)
                    .addParameter("id", profileId)
                    .executeAndFetch(ProfileEntityWithoutImages.class);
            conn.commit();
            log.info("Get profile by ID:{} result:{}", profileId, profile);
            return new ProfileEntity(profile.get(0));
        } catch (Exception e) {
            log.warn("Exception. getProfileById: {}", e.getMessage());
            throw new SelectDBException("Ошибка. Не удалось загрузить профиль");
        }
    }

    public void updateProfileById(ProfileEntity profile) {
        log.info("Update profile by ID. profile: {}", profile);
        try (org.sql2o.Connection conn = sql2o.open()) {

            int result = conn.createQuery(Update.updateProfileById)
                    .addParameter("age", profile.getAge())
                    .addParameter("gender", profile.getGender())
                    .addParameter("preference", profile.getPreference())
                    .addParameter("biography", profile.getBiography())
//                    .addParameter("tags", profile.getTagsAsString())
                    .addParameter("isFilled", profile.isFilled())
                    .addParameter("id", profile.getId())
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Update profile end. result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. updateProfileById: {}", e.getMessage());
            throw new UpdateDBException();
        }
    }

    public void dropProfileById(int id) {
        log.info("Drop profile by id. id: {}", id);
        try (org.sql2o.Connection conn = sql2o.open()) {

            int result = conn.createQuery(Delete.deleteProfileById)
                    .addParameter("id", id)
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Drop profile by id end. result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. dropProfileById: {}", e.getMessage());
            throw new DeleteDBException();
        }
    }

    public List<ProfileEntityWithoutImages> getAllProfiles() {
        log.info("Get all profiles");
        try (org.sql2o.Connection conn = sql2o.open()) {
            List<ProfileEntityWithoutImages> profiles = conn.createQuery(Select.selectProfile)
                    .executeAndFetch(ProfileEntityWithoutImages.class);
            conn.commit();
            log.info("Get all profiles done. Result size: {}", profiles.size());
            return profiles;
        } catch (Exception e) {
            log.warn("Exception. getAllProfiles: {}", e.getMessage());
            throw new SelectDBException("Ошибка. Не удалось загрузить профили");
        }
    }
}
