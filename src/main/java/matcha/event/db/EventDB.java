package matcha.event.db;

import lombok.extern.slf4j.Slf4j;
import matcha.Sql2oModel;
import matcha.db.crud.Insert;
import matcha.db.crud.Select;
import matcha.db.crud.Update;
import matcha.event.model.Event;
import matcha.event.model.EventWithUserInfo;
import matcha.exception.db.InsertDBException;
import matcha.exception.db.SelectDBException;
import matcha.exception.db.UpdateDBException;
import matcha.utils.EventType;
import org.sql2o.Sql2o;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
//@Service
//@RequiredArgsConstructor
//@NoArgsConstructor
public class EventDB {

    private final Sql2o sql2o = Sql2oModel.getSql2o();

    public Integer insertEvent(Event event) {
        log.info("Insert event: {}", event);
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {

            Integer eventId = conn.createQuery(Insert.insertEvent)
                    .addParameter("login", event.getLogin())
                    .addParameter("type", event.getType())
                    .addParameter("active", event.isActive())
                    .addParameter("time", new Timestamp(System.currentTimeMillis()))
                    .addParameter("data", event.getData())
                    .addParameter("needShow", event.isNeedShow())
                    .executeUpdate().getKey(Integer.class);
            conn.commit();

            log.info("Insert event result: {}", eventId);
            return eventId;
        } catch (Exception e) {
            log.warn("Exception. insertEvent: {}", e.getMessage());
            throw new InsertDBException();
        }
    }

    public List<Event> getAllEvents() {
        log.info("Get all events");
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {

            List<Event> events = conn.createQuery(Select.selectEvents)
                    .executeAndFetch(Event.class);
            conn.commit();

            log.info("Get all events count: {}", events.size());
            return events;
        } catch (Exception e) {
            log.warn("Exception. getAllEvents: {}", e.getMessage());
            throw new SelectDBException();
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

            log.info("Get Event by login result: {}", event.get(0));
            return event.get(0);
        } catch (Exception e) {
            log.warn("Exception. getLikeEvent: {}", e.getMessage());
            throw new SelectDBException();
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

            int updateResult = conn.createQuery(Update.updateHistoryNeedShowEvents)
                    .addParameter("login", fromLogin)
                    .addParameter("limit", limit)
                    .addParameter("offset", offset)
                    .executeUpdate().getResult();

            conn.commit();
            log.info("Get history Events result size: {} updateResult: {}", events.size(), updateResult);
            return events;
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception. getHistory: {}", e.getMessage());
            throw new SelectDBException();
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

            int updateResult = conn.createQuery(Update.updateNotificationNeedShowEvents)
                    .addParameter("data", toLogin)
                    .addParameter("limit", limit)
                    .addParameter("offset", offset)
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Get Notifications result size: {} updateResult: {}", events.size(), updateResult);
            return events;
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception. getNotifications: {}", e.getMessage());
            throw new SelectDBException();
        }
    }

    public List<Event> findActiveLikeOrUnlikeEvents(String login, String data) {
        log.info("Get findActiveEvents [login:{}] [data:{}]", login, data);
        try (org.sql2o.Connection conn = sql2o.open()) {
//TODO если прменять этот метод, надо переделать - искать не активные, а последние
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
            throw new SelectDBException();
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
            throw new UpdateDBException();
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

            log.info("Is CONNECTED Event result count: {}", events.size());
            if (events.size() == 0)
                return false;
            return events.get(0).getType().equals(EventType.CONNECTED);
        } catch (Exception e) {
            log.warn("Exception. isConnectedEvent: {}", e.getMessage());
            return false;
        }
    }

    public Integer getCountUserEventsByLogin(String login) {
        log.info("Get count user events [login:{}]", login);
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {

            List<Integer> events = conn.createQuery(Select.selectUserEventsCount)
                    .addParameter("login", login)
                    .executeAndFetch(Integer.class);
            conn.commit();
            log.info("Get count user events result: {}", events);
            return events.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception. getNotifications: {}", e.getMessage());
            throw new SelectDBException();
        }
    }

    public Integer getCountUnreadUserEventsByLogin(String login) {
        log.info("Get count user events [login:{}]", login);
        try (org.sql2o.Connection conn = sql2o.beginTransaction()) {

            List<Integer> eventsCount = conn.createQuery(Select.selectUnreadUserEventsCount)
                    .addParameter("login", login)
                    .executeAndFetch(Integer.class);
            conn.commit();
            log.info("Get count user events result: {}", eventsCount);
            return eventsCount.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception. getNotifications: {}", e.getMessage());
            throw new SelectDBException();
        }
    }
}
