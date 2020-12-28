package matcha.user.db;

import lombok.extern.slf4j.Slf4j;
import matcha.Sql2oModel;
import matcha.converter.Utils;
import matcha.db.crud.Insert;
import matcha.db.crud.Select;
import matcha.db.crud.Update;
import matcha.exception.db.InsertDBException;
import matcha.exception.db.SelectDBException;
import matcha.exception.db.UpdateDBException;
import matcha.exception.user.UserAuthException;
import matcha.model.SearchModel;
import matcha.user.model.UserEntity;
import matcha.user.model.UserEntity2;
import matcha.user.model.UserSearchEntity;
import matcha.user.model.UserUpdateEntity;
import org.sql2o.Sql2o;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Slf4j
public class UserDB {

    private final Sql2o sql2o = Sql2oModel.getSql2o();

    public Integer getUserCountByLogin(String login) {
        log.info("Get user count by login. Login: {}", login);

        try (org.sql2o.Connection conn = sql2o.open()) {
            List<Integer> login1 = conn.createQuery(Select.selectUsersCountByLogin)
                    .addParameter("login", login)
                    .executeAndFetch(Integer.class);
            conn.commit();
            log.info("Get user count by login result: {}", login1.get(0));
            return login1.get(0);
        } catch (Exception e) {
            log.warn("Exception. getUserCountByLogin: {}", e.getMessage());
            throw new SelectDBException();
        }
    }

    public UserEntity getUserByLogin(String login) {
        log.info("Get user by login [{}]", login);
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<UserEntity2> user = conn.createQuery(Select.selectUserByLogin)
                    .addParameter("login", login)
                    .executeAndFetch(UserEntity2.class);
            conn.commit();
            log.info("Get user by login result: {}", user.get(0));
            UserEntity userEntity = new UserEntity(user.get(0));
            return userEntity;
        } catch (Exception e) {
            log.warn("Exception. getUserByLogin: {}", e.getMessage());
            throw new SelectDBException("Пользователь " + login + " не найден");
        }
    }

    public int insertUser(UserEntity user) {
        log.info("Insert user: {}", user);
        try (org.sql2o.Connection conn = sql2o.open()) {

            Integer userId = conn.createQuery(Insert.insertUser)
                    .addParameter("login", user.getLogin())
                    .addParameter("password", user.getPasswordBytes())
                    .addParameter("activationCode", user.getActivationCode())
                    .addParameter("fname", user.getFname())
                    .addParameter("lname", user.getLname())
                    .addParameter("email", user.getEmail())
                    .addParameter("active", user.isActive())
                    .addParameter("blocked", user.isBlocked())
                    .addParameter("time", new Timestamp(System.currentTimeMillis()))
                    .addParameter("salt", user.getSalt())
                    .addParameter("profileId", user.getProfileId())
                    .executeUpdate().getKey(Integer.class);
            conn.commit();
            log.info("Insert user result userId: {}", userId);
            return userId;
        } catch (Exception e) {
            log.warn("Exception. insertUser: {}", e.getMessage());
            throw new InsertDBException();
        }
    }

    public void updateUserById(UserEntity user) {
        log.info("Update user: {}", user);
        try (org.sql2o.Connection conn = sql2o.open()) {

            int result = conn.createQuery(Update.updateUserById)
                    .addParameter("id", user.getId())
                    .addParameter("login", user.getLogin())
                    .addParameter("activationCode", user.getActivationCode())
                    .addParameter("fname", user.getFname())
                    .addParameter("lname", user.getLname())
                    .addParameter("email", user.getEmail())
                    .addParameter("active", user.isActive())
                    .addParameter("blocked", user.isBlocked())
                    .addParameter("time", user.getTime())
                    .addParameter("profileId", user.getProfileId())
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Update user end. result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. updateUserById: {}", e.getMessage());
            throw new UpdateDBException();
        }
    }


    public void updatePasswordUserById(UserEntity user) {
        log.info("Update full user: {}", user);
        try (org.sql2o.Connection conn = sql2o.open()) {

            int result = conn.createQuery(Update.updatePasswordUserById)
                    .addParameter("id", user.getId())
                    .addParameter("activationCode", user.getActivationCode())
                    .addParameter("active", user.isActive())
                    .addParameter("blocked", user.isBlocked())
                    .addParameter("salt", user.getSalt())
                    .addParameter("password", user.getPasswordBytes())
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Update user end. result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. updateUserById: {}", e.getMessage());
            throw new UpdateDBException();
        }
    }

    public List<UserEntity> getUserByToken(String activationCode) {
        log.info("Get user by Activation Code");
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<UserEntity> users = conn.createQuery(Select.selectUserByActivationCode)
                    .addParameter("activationCode", activationCode)
                    .executeAndFetch(UserEntity.class);
            conn.commit();
            log.info("Get user by Activation Code. Result user: {}", users.get(0));
            return users;
        } catch (Exception e) {
            log.info("Exception. getUserByToken: {}", e.getMessage());
            throw new SelectDBException("Ошибка авторизации");
        }
    }

    public List<UserEntity> checkUserByToken(String token) {
        log.info("Check user by Activation Code.");
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<UserEntity> users = conn.createQuery(Select.selectUsersCountByActivationCode)
                    .addParameter("activationCode", token)
                    .executeAndFetch(UserEntity.class);

            if (users != null && users.size() == 1) {
                int result = conn.createQuery(Update.updateUserTimeByToken)
                        .addParameter("token", token)
                        .addParameter("time", Calendar.getInstance().getTime())
                        .executeUpdate().getResult();
                log.info("Update user time by login end. Result: {}", result);
            }
            conn.commit();

            log.info("Check user by Activation Code. Result: {}", users);
            return users;
        } catch (Exception e) {
            log.info("Exception. checkUserByToken: {}", e.getMessage());
            throw new SelectDBException("Ошибка авторизации");
        }
    }

    public Integer checkUserByLoginAndToken(String login, String activationCode) {
        log.info("Get user count by login and activation code. login:{} activationCode:{}", login, activationCode);
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<Integer> usersCount = conn.createQuery(Select.selectUsersCountByLoginAndActivationCode)
                    .addParameter("login", login)
                    .addParameter("activationCode", activationCode)
                    .executeAndFetch(Integer.class);
            conn.commit();
            log.info("Get user count by login and activation code result: {}", usersCount.get(0));
            return usersCount.get(0);
        } catch (Exception e) {
            log.info("Exception. checkUserByLoginAndToken: {}", e.getMessage());
            throw new UserAuthException("Пользователь не найден");
        }
    }

    public void updateUserByLogin(UserUpdateEntity user) {
        log.info("Update user by login: {}", user);
        try (org.sql2o.Connection conn = sql2o.open()) {

            int result = conn.createQuery(Update.updateUserByLogin)
                    .addParameter("login", user.getLogin())
                    .addParameter("fname", user.getFname())
                    .addParameter("lname", user.getLname())
                    .addParameter("email", user.getEmail())
                    .addParameter("time", user.getTime())
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Update user by login end. result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. updateUserByActivationCode: {}", e.getMessage());
            throw new UpdateDBException();
        }
    }

    public void updateUserByLogin(UserEntity user) {
        log.info("Update user by login: {}", user);
        try (org.sql2o.Connection conn = sql2o.open()) {

            int result = conn.createQuery(Update.updateUserByLogin)
                    .addParameter("login", user.getLogin())
                    .addParameter("fname", user.getFname())
                    .addParameter("lname", user.getLname())
                    .addParameter("email", user.getEmail())
                    .addParameter("time", user.getTime())
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Update user by login end. result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. updateUserByActivationCode: {}", e.getMessage());
            throw new UpdateDBException();
        }
    }

    public Integer getUserProfileIdByLogin(String login) {
        log.info("Get profile id by user login: {}", login);
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<Integer> profileId = conn.createQuery(Select.selectUserProfileIdByLogin)
                    .addParameter("login", login)
                    .executeAndFetch(Integer.class);
            conn.commit();
            log.info("Get profile id by user login:{} result:{}", login, profileId.get(0));
            return profileId.get(0);
        } catch (Exception e) {
            log.warn("Exception. getUserProfileIdByLogin: {}", e.getMessage());
            throw new SelectDBException();
        }
    }

    public List<UserEntity> getAllUsers() {
        log.info("Get all users");
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<UserEntity> users = conn.createQuery(Select.selectUsers)
                    .executeAndFetch(UserEntity.class);
            conn.commit();
            log.info("Get all users result count: {}", users.size());
            return users;
        } catch (Exception e) {
            log.warn("Exception. getLocations: {}", e.getMessage());
            throw new SelectDBException();
        }
    }

    public List<UserSearchEntity> getUsersWithFilters(SearchModel searchModel) {
        log.info("Get users with filters: {}", searchModel);

        String limitAndOffset = " LIMIT :limit OFFSET :offset ";

        String orderBy = Utils.prepareOrderBy(searchModel);
        log.info("OrderBy: {}", orderBy);

        try (org.sql2o.Connection conn = sql2o.open()) {

            List<UserSearchEntity> users;
            if (searchModel.getTags().size() == 0) {
                users = conn.createQuery(Select.selectUsersWithoutTagsWithFilters + orderBy + limitAndOffset)
                        .addParameter("x", searchModel.getUserLocation().getX())
                        .addParameter("y", searchModel.getUserLocation().getY())
                        .addParameter("ageMax", searchModel.getMaxAge())
                        .addParameter("ageMin", searchModel.getMinAge())
                        .addParameter("radius", searchModel.getDeltaRadius())
                        .addParameter("limit", searchModel.getLimit())
                        .addParameter("offset", searchModel.getOffset())
                        .addParameter("login", searchModel.getLogin())
                        .addParameter("preferenceGender", searchModel.getPreference())
                        .executeAndFetch(UserSearchEntity.class);
                conn.commit();
            } else {
                users = conn.createQuery(Select.selectUsersWithFilters + orderBy + limitAndOffset)
                        .addParameter("x", searchModel.getUserLocation().getX())
                        .addParameter("y", searchModel.getUserLocation().getY())
                        .addParameter("ageMax", searchModel.getMaxAge())
                        .addParameter("ageMin", searchModel.getMinAge())
                        .addParameter("radius", searchModel.getDeltaRadius())
                        .addParameter("limit", searchModel.getLimit())
                        .addParameter("offset", searchModel.getOffset())
                        .addParameter("tagIds", searchModel.getTags())
                        .addParameter("login", searchModel.getLogin())
                        .addParameter("preferenceGender", searchModel.getPreference())
                        .executeAndFetch(UserSearchEntity.class);
                conn.commit();
            }
            log.info("Get users with filters. Result count: {}", users.size());
            return users;
        } catch (Exception e) {
            log.warn("Exception. getUsersWithFilters: {}", e.getMessage());
            throw new SelectDBException();
        }
    }

    public void updateTimeByLogin(String userLogin) {
        log.info("Update user time by login: {}", userLogin);
        try (org.sql2o.Connection conn = sql2o.open()) {

            int result = conn.createQuery(Update.updateUserTimeByLogin)
                    .addParameter("login", userLogin)
                    .addParameter("time", Calendar.getInstance().getTime())
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Update user time by login end. Result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. updateTimeByLogin: {}", e.getMessage());
            throw new UpdateDBException();
        }
    }
}
