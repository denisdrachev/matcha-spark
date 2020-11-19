package matcha.location.db;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.Sql2oModel;
import matcha.db.crud.Insert;
import matcha.db.crud.Select;
import matcha.db.crud.Update;
import matcha.exception.db.location.GetActiveLocationByLoginException;
import matcha.exception.db.location.GetLocationsException;
import matcha.exception.db.location.InsertLocationException;
import matcha.exception.db.location.UpdateLocationException;
import matcha.location.model.Location;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.sql2o.Sql2o;

import java.util.List;

@Slf4j
@Service
//@RequiredArgsConstructor
@NoArgsConstructor
public class LocationDB {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private final Sql2o sql2o = Sql2oModel.getSql2o();

    public List<Location> getLocations() {
        log.info("Get all locations");
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {

            List<Location> locations = conn.createQuery(Select.selectLocations)
                    .executeAndFetch(Location.class);
            conn.commit();

//            List<Location> query = jdbcTemplate.query(Select.selectLocations, new LocationRowMapper());
            log.info("Get all locations result count: {}", locations.size());
            return locations;
        } catch (Exception e) {
            log.warn("Exception. getLocations: {}", e.getMessage());
            throw new GetLocationsException();
        }
    }

    public void insertLocation(Location location) {
        log.info("Insert location: {}", location);
        try (org.sql2o.Connection conn = sql2o.open()) {

            Integer insert = conn.createQuery(Insert.insertLocation)
                    .addParameter("profileId", location.getProfileId())
                    .addParameter("x", location.getX())
                    .addParameter("y", location.getY())
                    .addParameter("time", location.getTime())
                    .addParameter("active", location.isActive())
                    .executeUpdate().getResult();
            conn.commit();

//            int insert = jdbcTemplate.update(Insert.insertLocation, location.getProfileId(),
//                    location.getX(), location.getY(), location.getTime(), location.isActive());
            log.info("Insert location result: {}", insert);
        } catch (Exception e) {
            log.warn("Exception. insertLocation: {}", e.getMessage());
            throw new InsertLocationException();
        }
    }

    public Location getLocationByUserIdAndActive(Integer profileId) {
        log.info("Get active location by user id: {}", profileId);
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {

            List<Location> locations = conn.createQuery(Select.selectLocationByUserIdAndActive)
                    .addParameter("profileId", profileId)
                    .executeAndFetch(Location.class);
            conn.commit();

//            Location location = jdbcTemplate.queryForObject(Select.selectLocationByUserIdAndActive,
//                    new LocationRowMapper(), userId);
            log.info("Get active location by login done. Result: {}", locations);
            if (locations.isEmpty())
                return null;
            return locations.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception. getActiveLocationByLogin: {}", e.getMessage());
            throw new GetActiveLocationByLoginException();
        }
    }

    public Integer updateLocation(Location location) {
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {
            log.info("Update location {}", location);

            Integer update = conn.createQuery(Update.updateLocationById)
                    .addParameter("active", location.isActive())
                    .addParameter("id", location.getId())
                    .executeUpdate().getResult();
            conn.commit();

//            int update = jdbcTemplate.update(Update.updateLocationById, location.isActive(), location.getId());
            log.info("Update location done. Result: {}", update);
            return update;
        } catch (Exception e) {
            log.warn("Exception. updateLocation: {}", e.getMessage());
            throw new UpdateLocationException();
        }
    }

    public void updateActiveLocationByLogin(boolean isActive, String login) {
        log.info("Update active:{} location by login: {}", isActive, login);
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {

            Integer update = conn.createQuery(Update.updateLocationByLogin)
                    .addParameter("active", isActive)
                    .addParameter("login", login)
                    .executeUpdate().getResult();
            conn.commit();

//            int update = jdbcTemplate.update(Update.updateLocationByLogin, isActive, login);
            log.info("Update active location by login result: {}", update);
        } catch (Exception e) {
            log.warn("Exception. updateActiveLocationByLogin: {}", e.getMessage());
//            throw new UpdateLocationException();
        }
    }
}
