package matcha.profile.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.Sql2oModel;
import matcha.db.crud.Drop;
import matcha.db.crud.Insert;
import matcha.db.crud.Select;
import matcha.db.crud.Update;
import matcha.exception.context.UserRegistrationException;
import matcha.exception.db.DropProfileByIdDBException;
import matcha.exception.db.GetProfileByIdDBException;
import matcha.exception.db.GetProfileCountByIdDBException;
import matcha.exception.db.UpdateProfileByIdDBException;
import matcha.profile.model.ProfileEntity;
import matcha.profile.model.ProfileEntityWithoutImages;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.sql2o.Sql2o;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileDB {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private final Sql2o sql2o = Sql2oModel.getSql2o();

    public Integer insertEmptyProfile() {
        log.info("Create empty profile");
        try (org.sql2o.Connection conn = sql2o.open()) {

            Integer profileId = conn.createQuery(Insert.insertProfile)
                    .addParameter("age", 0)
                    .addParameter("gender", 0)
                    .addParameter("preference", "")
                    .addParameter("biography", "")
                    .addParameter("tags", "")
                    .addParameter("isFilled", false)
                    .executeUpdate().getKey(Integer.class);
            conn.commit();

           /* KeyHolder keyHolder = new GeneratedKeyHolder();
            int update = jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(Insert.insertProfile, new String[]{"id"});
                    ps.setNull(1, Types.INTEGER);
                    ps.setNull(2, Types.INTEGER);
                    ps.setNull(3, Types.VARCHAR);
                    ps.setNull(4, Types.VARCHAR);
                    ps.setNull(5, Types.VARCHAR);
                    ps.setBoolean(6, false);
                    return ps;
                }
            }, keyHolder);*/
            log.info("Create empty profile result: {}", profileId);
            return profileId;
        } catch (Exception e) {
            log.warn("Exception. insertEmptyProfile: {}", e.getMessage());
            e.printStackTrace();
            throw new UserRegistrationException();
        }
    }

    public Integer getProfileCountById(int profileId) {
        log.info("Get profile cound by ID. profileId: {}", profileId);
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {

            List<Integer> count = conn.createQuery(Select.selectProfilesCountById)
                    .addParameter("id", profileId)
                    .executeAndFetch(Integer.class);
            conn.commit();

//            Integer integer = jdbcTemplate.queryForObject(Select.selectProfilesCountById,
//                    Integer.class, profileId);
            log.info("Get profile cound by ID. profileId: {} count: {}", profileId, count.get(0));
            return count.get(0);
        } catch (Exception e) {
            log.warn("Exception. getProfileCountById: {}", e.getMessage());
            throw new GetProfileCountByIdDBException();
        }
    }

    public ProfileEntity getProfileById(int profileId) {
        log.info("Get profile by ID: {}", profileId);
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<ProfileEntityWithoutImages> profile = conn.createQuery(Select.selectProfileById)
                    .addParameter("id", profileId)
                    .executeAndFetch(ProfileEntityWithoutImages.class);
            conn.commit();


//            ProfileEntity profile = jdbcTemplate.queryForObject(Select.selectProfileById,
//                    new ProfileRowMapper(), profileId);
            log.info("Get profile by ID:{} result:{}", profileId, profile);

            return new ProfileEntity(profile.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception. getProfileById: {}", e.getMessage());
            throw new GetProfileByIdDBException("Ошибка. Не удалось загрузить профиль");
        }
    }

    public void updateProfileById(ProfileEntity profile) {
        log.info("Update profile by ID. profile: {}", profile);
        try (org.sql2o.Connection conn = sql2o.open()) {

            int result = conn.createQuery(Update.updateProfileById)
                    .addParameter("age", profile.getAge())
                    .addParameter("gender", profile.getGender())
                    .addParameter("preference", profile.getPreferenceAsString())
                    .addParameter("biography", profile.getBiography())
                    .addParameter("tags", profile.getTagsAsString())
                    .addParameter("isFilled", profile.isFilled())
                    .addParameter("id", profile.getId())
                    .executeUpdate().getResult();
            conn.commit();

//            int update = jdbcTemplate.update(Update.updateProfileById,
//                    profile.getAge(), profile.getGender(), profile.getPreferenceAsString(),
//                    profile.getBiography(), profile.getTagsAsString(), profile.isFilled(), profile.getId());
            log.info("Update profile end. result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. updateProfileById: {}", e.getMessage());
            e.printStackTrace();
            throw new UpdateProfileByIdDBException();
        }
    }

    public void dropProfileById(int id) {
        log.info("Drop profile by id. id: {}", id);
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {

            int result = conn.createQuery(Drop.deleteProfileById)
                    .addParameter("id", id)
                    .executeUpdate().getResult();
            conn.commit();

//            int drop = jdbcTemplate.update(Drop.deleteProfileById, id);
            log.info("Drop profile by id end. result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. dropProfileById: {}", e.getMessage());
            throw new DropProfileByIdDBException();
        }
    }
}
