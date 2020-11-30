package matcha.user.manipulation;

import lombok.extern.slf4j.Slf4j;
import matcha.converter.Utils;
import matcha.exception.context.UserRegistrationException;
import matcha.exception.db.InsertDBException;
import matcha.exception.db.UpdateDBException;
import matcha.exception.user.UserAuthException;
import matcha.location.model.Location;
import matcha.location.service.LocationService;
import matcha.model.SearchModel;
import matcha.response.Response;
import matcha.response.ResponseOk;
import matcha.user.db.UserDB;
import matcha.user.model.UserEntity;
import matcha.user.model.UserInfo;
import matcha.user.model.UserSearchEntity;
import matcha.user.model.UserUpdateEntity;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

//import org.springframework.stereotype.Service;

@Slf4j
//@Service
//@RequiredArgsConstructor
public class UserManipulator {

    private final UserDB userDB = new UserDB();
    private final LocationService locationService = LocationService.getInstance();

    public void userRegistry(UserEntity user) {
        userDB.insertUser(user);
    }

    //TODO рефакторинг
    public Response userLogin(UserInfo userLogin) {
        UserEntity user = userDB.getUserByLogin(userLogin.getLogin());
        try {
            Location location = new Location(userLogin.getLocation());
            locationService.initLocationAndUpdate(location, user.getProfileId(), false, false);
            if (user.isActive() && !user.isBlocked()) {
                if (Utils.checkPassword(userLogin.getPassword(), user.getSalt(), user.getPasswordBytes())) {
//                    user.setActivationCode("TEST_TEST_TEST");
                    //TODO первнуть строку
                    userUpdateToken(user);
                    return new ResponseOk(user.getActivationCode());
                } else {
                    log.info("Логин или пароль неверны. User: {}", userLogin);
                    throw new UserAuthException("Некорректное имя пользователя или пароль");
                }
            } else {
                String format = String.format("Пользователь %s заблокирован или неактивен", userLogin.getLogin());
                log.info(format);
                throw new UserAuthException(format);
            }
        } catch (InsertDBException | UpdateDBException ile) {
            String format = String.format("Ошибка авторизации пользователя %s.", userLogin.getLogin());
            log.info(format);
            ile.printStackTrace();
            throw new UserAuthException(format);
        }
    }

    public void userUpdateToken(UserEntity user) {
        user.setActivationCode(UUID.randomUUID().toString());
        user.setTime(Calendar.getInstance().getTime());
        userDB.updateUserById(user);
    }

    //TODO подумать, мб переделать, чтобы изолировать от получения пользователя из бд
    public void userUpdate(UserUpdateEntity user) {
        userDB.updateUserByLogin(user);
    }

    public void userUpdate(UserEntity user) {
        userDB.updateUserByLogin(user);
    }

    public UserEntity getUserByLogin(String login) {
        return userDB.getUserByLogin(login);
    }

    public UserEntity getUserByToken(String token) {
        return userDB.getUserByToken(token);
    }

    public String checkUserByToken(String token) {
        List<String> users = userDB.checkUserByToken(token);
        if (users.size() != 1) {
            throw new UserAuthException();
        }
        return users.get(0);
    }

    public boolean activationUserByToken(String token) {
        try {
            UserEntity userByToken = getUserByToken(token);
            userByToken.setActive(true);
            userByToken.setActivationCode(null);
            userUpdate(userByToken);
            return true;
        } catch (Exception e) {
            log.info("Failed activation user by token: {}", token);
            return false;
        }
    }

    public void checkUserExistByLogin(String login) {
        Integer userCountByLogin = userDB.getUserCountByLogin(login);
        if (userCountByLogin != 0)
            throw new UserRegistrationException("Пользователь с ником " + login + " уже существует");

    }

    public boolean isUserExistByLogin(String login) {
        Integer userCountByLogin = userDB.getUserCountByLogin(login);
        if (userCountByLogin != 0)
            return true;
        return false;
    }

    public Integer getUserProfileId(String login) {
        return userDB.getUserProfileIdByLogin(login);
    }

    public void checkUserByLoginAndActivationCode(String login, String token) {
        Integer userCount = userDB.checkUserByLoginAndToken(login, token);
        if (userCount != 1)
            throw new UserAuthException("Пользователь не найден");
    }

    public List<UserEntity> getAllUsers() {
        return userDB.getAllUsers();
    }

    public List<UserSearchEntity> getUsersWithFilters(SearchModel searchModel) {
        return userDB.getUsersWithFilters(searchModel);
    }
}
