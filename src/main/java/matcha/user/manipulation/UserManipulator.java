package matcha.user.manipulation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.converter.Utils;
import matcha.exception.context.UserAlreadyExistException;
import matcha.exception.db.UpdateUserByIdDBException;
import matcha.exception.db.location.InsertLocationException;
import matcha.exception.user.*;
import matcha.location.manipulation.LocationManipulator;
import matcha.response.Response;
import matcha.response.ResponseOk;
import matcha.user.db.UserDB;
import matcha.user.model.UserEntity;
import matcha.user.model.UserInfo;
import matcha.user.model.UserUpdateEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserManipulator {

    private final UserDB userDB = new UserDB();
    private final LocationManipulator locationManipulator = new LocationManipulator();

    public void userRegistry(UserEntity user) {
        userDB.insertUser(user);
    }

    //TODO рефакторинг
    public Response userLogin(UserInfo userLogin) {
        UserEntity user = userDB.getUserByLogin(userLogin.getLogin());
        userLogin.getLocation().setProfileId(user.getId());
        try {
            locationManipulator.insertLocation(userLogin.getLocation());

            if (user.isActive() && !user.isBlocked()) {
                if (Utils.checkPassword(userLogin.getPassword(), user.getSalt(), user.getPasswordBytes())) {
//                    user.setActivationCode("TEST_TEST_TEST");
                    //TODO первнуть строку
                    userUpdateToken(user);
                    return new ResponseOk(user.getActivationCode()/*, user.getLogin()*/);
                } else {
                    log.info("Логин или пароль неверны. User: {}", userLogin);
                    throw new UserLoginOrPasswordIncorrectException();
                }
            } else {
                String format = String.format("Пользователь %s заблокирован или неактивен", userLogin.getLogin());
                log.info(format);
                throw new UserBlockedOrDisabledException(format);
            }
        } catch (InsertLocationException | UpdateUserByIdDBException ile) {
            String format = String.format("Ошибка авторизации пользователя %s.", userLogin.getLogin());
            log.info(format);
            ile.printStackTrace();
            throw new UserLoginException(format);
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

    public void checkUserByToken(String token) {
        Integer count = userDB.checkUserByToken(token);
        if (count == 0) {
            throw new UserAuthException();
        }
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
            throw new UserAlreadyExistException("Пользователь с ником " + login + " уже существует");

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
            throw new UserNotFoundException();
    }

    public List<UserEntity> getAllUsers() {
        return userDB.getAllUsers();
    }
}
