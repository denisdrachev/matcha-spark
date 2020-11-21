package matcha.event.db;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.Sql2oModel;
import matcha.db.crud.Insert;
import matcha.db.crud.Select;
import matcha.db.crud.Update;
import matcha.event.model.Event;
import matcha.event.model.EventWithUserInfo;
import matcha.exception.db.EventNotFoundDBException;
import matcha.exception.db.InsertEventDBException;
import matcha.exception.db.LoadEventsException;
import matcha.exception.db.UpdateEventDBException;
import matcha.utils.EventType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.sql2o.Sql2o;

import javax.lang.model.element.TypeElement;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
//@RequiredArgsConstructor
@NoArgsConstructor
public class EventDB {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private final Sql2o sql2o = Sql2oModel.getSql2o();

    //    public Integer getImageCountById(int imageId) {
//        log.info("Get image count by id: {}", imageId);
//        try {
//            Integer imagesCount = jdbcTemplate.queryForObject(Select.selectImagesCountById,
//                    Integer.class, imageId);
//            log.info("Get image count by id {} result: {}", imageId, imagesCount);
//            return imagesCount;
//        } catch (Exception e) {
//            log.warn("Exception. getImageCountById: {}", e.getMessage());
//            throw new GetImageCountByIdDBException();
//        }
//    }
//
//    public Image getImageById(String imageId) {
//        log.info("Get image by id: {}", imageId);
//        try {
//            Image image = jdbcTemplate.queryForObject(Select.selectImageById, new ImageRowMapper(), imageId);
//            log.info("Get image by id {} result: {}", imageId, image);
//            return image;
//        } catch (Exception e) {
//            log.warn("Exception. getImageById: {}", e.getMessage());
//            throw new GetImageByIdDBException();
//        }
//    }
//
    public Integer insertEvent(Event event) {
        log.info("Insert event: {}", event);
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {

            Integer eventId = conn.createQuery(Insert.insertEvent)
                    .addParameter("login", event.getLogin())
                    .addParameter("type", event.getType())
                    .addParameter("active", event.isActive())
                    .addParameter("time", new Timestamp(System.currentTimeMillis()))
                    .addParameter("data", event.getData())
                    .executeUpdate().getKey(Integer.class);
            conn.commit();

            /*KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(Insert.insertEvent, new String[]{"id"});
                    ps.setString(1, event.getType());
                    ps.setString(2, event.getLogin());
                    ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                    ps.setBoolean(4, event.isActive());
                    ps.setString(5, event.getData());
                    return ps;
                }
            }, keyHolder);*/
            log.info("Insert event result: {}", eventId);
            return eventId;
        } catch (Exception e) {
            log.warn("Exception. insertEvent: {}", e.getMessage());
            throw new InsertEventDBException();
        }
    }

    //
//    public void updateImageById(Image image) {
//        log.info("Update image by id: {}", image);
//        try {
//            int update = jdbcTemplate.update(Update.updateImageById, image.getSrc(), image.isAvatar(), image.getId());
//            log.info("Update image by id result: {}", update);
//        } catch (Exception e) {
//            log.warn("Exception. updateImageById: {}", e.getMessage());
//            throw new UpdateImageByIdDBException();
//        }
//    }
//
//    public void dropImageById(String id) {
//        try {
//            log.info("Drop image by id: ".concat(id));
//            int drop = jdbcTemplate.update(Drop.deleteImageById, id);
//            log.info("Drop image by id result: ".concat(String.valueOf(drop)));
//        } catch (Exception e) {
//            log.warn("Exception. insertImage: {}", e.getMessage());
//            throw new DropImageByIdDBException();
//        }
//    }
//
//    public List<Image> getImagesByProfileId(int profileId) {
//        log.info("Get images by profile id: {}", profileId);
//        try {
//            List<Image> images = jdbcTemplate.query(Select.selectImageByProfileId, new ImageRowMapper(), profileId);
//            log.info("Get images by id profile: {}", images);
//            return images;
//        } catch (Exception e) {
//            log.warn("Exception. getImagesByProfileId: {}", e.getMessage());
//            throw new LoadImageException();
//        }
//    }
//
    public List<Event> getAllEvents() {
        log.info("Get all events");
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {

            List<Event> events = conn.createQuery(Select.selectEvents)
                    .executeAndFetch(Event.class);
            conn.commit();

//            List<Event> events = jdbcTemplate.query(Select.selectEvents, new EventRowMapper());
            log.info("Get all events count: {}", events.size());
            return events;
        } catch (Exception e) {
            log.warn("Exception. getAllEvents: {}", e.getMessage());
            throw new LoadEventsException();
        }
    }

    public Event getLikeEvent(String fromLogin, String toLogin) {
        log.info("Get Event by login: [fromLogin:{}][toLogin:{}]", fromLogin, toLogin);
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {

            List<Event> event = conn.createQuery(Select.selectEventByLogin)
                    .addParameter("type1", EventType.LIKE)
                    .addParameter("type2", EventType.UNLIKE)
                    .addParameter("login", fromLogin)
                    .addParameter("data", toLogin)
                    .executeAndFetch(Event.class);
            conn.commit();

//            Event event = jdbcTemplate.queryForObject(Select.selectEventByLogin, new EventRowMapper(),
//                    EventType.IMAGE_LIKE, fromLogin, toLogin);
            log.info("Get Event by login result: {}", event.get(0));
            return event.get(0);
        } catch (Exception e) {
            log.warn("Exception. getLikeEvent: {}", e.getMessage());
            throw new EventNotFoundDBException();
        }
    }

    public List<EventWithUserInfo> getHistory(String fromLogin, Integer limit, Integer offset) {
        log.info("Get Events [fromLogin:{}]", fromLogin);
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {

            List<EventWithUserInfo> events = conn.createQuery(Select.selectHistoryEvents)
                    .addParameter("login", fromLogin)
                    .addParameter("limit", limit)
                    .addParameter("offset", offset)
                    .executeAndFetch(EventWithUserInfo.class);
            conn.commit();
            log.info("Get Events result size: {}", events.size());
            return events;
        } catch (Exception e) {
            log.warn("Exception. getHistory: {}", e.getMessage());
            throw new EventNotFoundDBException();
        }
    }

    //Поиск лайка пользователя
    public boolean isLikeEvent(String fromLogin, String toLogin) {
        log.info("Is Like Event: [fromLogin:{}][toLogin:{}]", fromLogin, toLogin);
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {

            List<Event> events = conn.createQuery(Select.selectEventByLogin)
                    .addParameter("type1", EventType.LIKE)
                    .addParameter("type2", EventType.UNLIKE)
                    .addParameter("login", fromLogin)
                    .addParameter("data", toLogin)
                    .executeAndFetch(Event.class);
            conn.commit();

//            Event event = jdbcTemplate.queryForObject(Select.selectEventByLogin, new EventRowMapper(),
//                    EventType.IMAGE_LIKE, fromLogin, toLogin);
            log.info("Is Like Event result count: {}", events.size());
            if (events.size() == 0)
                return false;
            return events.get(0).getType().equals(EventType.LIKE);
        } catch (Exception e) {
            log.warn("Exception. isLikeEvent: {}", e.getMessage());
            return false;
        }
    }

    public List<EventWithUserInfo> getNotifications(String toLogin, Integer limit, Integer offset) {
        log.info("Get Notifications [toLogin:{}]", toLogin);
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {

            List<EventWithUserInfo> events = conn.createQuery(Select.selectNotificationEvents)
                    .addParameter("data", toLogin)
                    .addParameter("limit", limit)
                    .addParameter("offset", offset)
                    .executeAndFetch(EventWithUserInfo.class);
            conn.commit();
            log.info("Get Notifications result size: {}", events.size());
            return events;
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception. getNotifications: {}", e.getMessage());
            throw new EventNotFoundDBException();
        }
    }

    public List<Event> findActiveLikeOrUnlikeEvents(String login, String data) {
        log.info("Get findActiveEvents [login:{}] [data:{}]", login, data);
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<Event> events = conn.createQuery(Select.selectActiveLikes)
                    .addParameter("type1", EventType.LIKE)
                    .addParameter("type2", EventType.UNLIKE)
                    .addParameter("data", data)
                    .addParameter("login", login)
                    .executeAndFetch(Event.class);
            conn.commit();
            log.info("Get findActiveEvents result size: {}", events.size());
            return events;
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception. findActiveEvents: {}", e.getMessage());
            throw new EventNotFoundDBException();
        }
    }

    public void updateEventActiveById(Event event) {
        log.info("Update event by ID: {}", event);
        try (org.sql2o.Connection conn = sql2o.open()) {

            int eventId = conn.createQuery(Update.updateEventActiveById)
                    .addParameter("active", event.isActive())
                    .addParameter("id", event.getId())
                    .executeUpdate().getResult();
            conn.commit();

            log.info("Update event by ID result: {}", eventId);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception. saveEventById: {}", e.getMessage());
            throw new UpdateEventDBException();
        }
    }

    public boolean isConnectedEvent(String fromLogin, String toLogin) {
        log.info("Is CONNECTED Event: [fromLogin:{}][toLogin:{}]", fromLogin, toLogin);
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<Event> events = conn.createQuery(Select.selectEventConnectedOrUnlike)
                    .addParameter("type1", EventType.CONNECTED)
                    .addParameter("type2", EventType.UNLIKE)
                    .addParameter("login", fromLogin)
                    .addParameter("data", toLogin)
                    .executeAndFetch(Event.class);
            conn.commit();

//            Event event = jdbcTemplate.queryForObject(Select.selectEventByLogin, new EventRowMapper(),
//                    EventType.IMAGE_LIKE, fromLogin, toLogin);
            log.info("Is CONNECTED Event result count: {}", events.size());
            if (events.size() == 0)
                return false;
            return events.get(0).getType().equals(EventType.CONNECTED);
        } catch (Exception e) {
            log.warn("Exception. isConnectedEvent: {}", e.getMessage());
            return false;
        }
    }
}
