package matcha.db;//package matcha.db;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import matcha.converter.Utils;
//import matcha.mail.MailService;
//import matcha.location.model.Location;
//import matcha.model.MyObject;
//import matcha.model.UserAndProfile;
//import matcha.profile.model.ProfileModel;
//import matcha.response.ResponseError;
//import matcha.response.ResponseOk;
//import matcha.response.ResponseOkData;
//import matcha.user.model.UserEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.Calendar;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class EntityActions {
//
//    private final EntityManipulator entityManipulator;
//    private final MailService mailMailService;
//
//    public Object userRegistry(UserEntity user) {
//
//        Optional<Integer> userExist = entityManipulator.getUserCountByLogin(user.getLogin());
//        if (userExist.isEmpty() || userExist.get() != 0) {
//            StringBuilder sb = new StringBuilder()
//                    .append("userRegistry. User exist: ");
//            log.info(sb.toString().concat(user.toString()));
//            sb.append(user.getLogin());
//            return new ResponseError("error", sb.toString());
//        }
//
//        Optional<Integer> emptyProfile = entityManipulator.insertEmptyProfile();
//        if (emptyProfile.isEmpty()) {
//            StringBuilder sb = new StringBuilder()
//                    .append("userRegistry. Error create user profile: ");
//            log.error(sb.toString().concat(user.toString()));
//            sb.append(user.getLogin());
//            return new ResponseError("error", sb.toString());
//        }
//        user.setProfileId(emptyProfile.get());
//
//
//        Object userCreated = entityManipulator.insertUser(user);
//        if (userCreated instanceof ResponseError)
//            return userCreated;
//
//        if (user.getLocation() != null) {
//            user.getLocation().setUser(userCreated);
//            entityManipulator.insertLocations(user.getLocation());
//        }
//
//        boolean b = mailMailService.sendRegistrationMail(user.getEmail(), user.getActivationCode());
//        if (!b) {
//            Optional<Integer> userCountByLogin = entityManipulator.getUserCountByLogin(user.getLogin());
//            if (userCountByLogin.isPresent() && userCountByLogin.get() == 1) {
//                Optional<Integer> integer = entityManipulator.dropUserByLogin(user.getLogin());
//                if (integer.isEmpty() || integer.get() != 1)
//                    return new ResponseError("error", "NO NAME ERROR!");
//            }
//        }
//        return new ResponseOk("ok", "CREATED", user.getLogin());
//    }
//
////    publi Object userUpdate(User user) {
////        Optional<Integer> userExist = entityManipulator.getUserCountByLogin(user.getLogin());
////        if (userExist.isEmpty() || userExist.get() != 0) {
////            StringBuilder sb = new StringBuilder()
////                    .append("userRegistry. User exist: ");
////            log.info(sb.toString().concat(user.toString()));
////            sb.append(user.getLogin());
////            return new ResponseError("error", sb.toString());
////        }
////        Optional<Integer> userCreated = entityManipulator.createUser(user);
////        if (userCreated.isEmpty() || userCreated.get() != 1) {
////            StringBuilder sb = new StringBuilder()
////                    .append("userRegistry. Error create user: ");
////            log.error(sb.toString().concat(user.toString()));
////            sb.append(user.getLogin());
////            return new ResponseError("error", sb.toString());
////        }
////        boolean b = mailSender.sendRegistrationMail(user.getEmail(), user.getActivationCode());
////        if (!b) {
////            Optional<Integer> userCountByLogin = entityManipulator.getUserCountByLogin(user.getLogin());
////            if (userCountByLogin.isPresent() && userCountByLogin.get() == 1) {
////                Optional<Integer> integer = entityManipulator.dropUserByLogin(user.getLogin());
////                if (integer.isEmpty() || integer.get() != 1)
////                    return new ResponseError("error", "NO NAME ERROR!");
////            }
////        }
////        return new ResponseOk("ok", "CREATED", user.getLogin());
////    }
//
//    public boolean getVerificationToken(String uuid) {
//        Optional<Integer> userCountByActivationCode = entityManipulator.getUserCountByActivationCode(uuid);
//        if (userCountByActivationCode.isEmpty() || userCountByActivationCode.get() != 1)
//            return false;
//        Optional<UserEntity> userByActivationCode = entityManipulator.getUserByActivationCode(uuid);
//        if (userByActivationCode.isEmpty())
//            return false;
//        userByActivationCode.get().setActivationCode(null);
//        userByActivationCode.get().setActive(true);
//        MyObject myObject = entityManipulator.updateUserById(userByActivationCode.get());
//        return !(myObject instanceof ResponseError);
//    }
//
//    public Object userLogin(String login, String password, Location location) {
//        String message = "";
//        MyObject userByLoginObject = entityManipulator.getUserByLogin(login);
//        if (userByLoginObject instanceof ResponseError)
//            return userByLoginObject;
//        UserEntity user = (UserEntity) userByLoginObject;
//        location.setUser(user.getId());
//        entityManipulator.insertLocations(location);
//
//        if (user.isActive() && !user.isBlocked()) {
//            if (Utils.checkPassword(password, user.getSalt(), user.getPasswordBytes())) {
//                user.setActivationCode(UUID.randomUUID().toString());
//                user.setTime(Calendar.getInstance().getTime());
//                //сохранить location в базу
//                MyObject updateRes = entityManipulator.updateUserById(user);
//                if (updateRes instanceof ResponseError) {
//                    ResponseError error = (ResponseError) updateRes;
//                    error.setMessage("Ошибка авторизации");
//                    return error;
//                } else {
//                    return new ResponseOk("ok", user.getActivationCode(), user.getLogin());
//                }
//            } else
//                message = "Логин или пароль неверны";
//        } else
//            message = "Пользователь ".concat(login).concat(" заблокирован или неактивен");
//        log.info("userLogin. ".concat(message));
//        return new ResponseError("error", message);
//    }
//
//    //TODO подумать, мб переделать, чтобы изолировать от получения пользователя из бд
//    public Object userUpdate(UserEntity user) {
//
//        Optional<UserEntity> userByActivationCode = entityManipulator.getUserByActivationCode(user.getActivationCode());
//
//        if (userByActivationCode.isEmpty()) {
//            log.warn("User with activationCode '{}' not found!", user.getActivationCode());
//            return new ResponseError("error", "Перелогинься!");
//        }
//        user.setProfileId(userByActivationCode.get().getProfileId());
//        user.getLocation().setUser(userByActivationCode.get().getId());
//        user.setActive(userByActivationCode.get().isActive());
//        user.setBlocked(userByActivationCode.get().isBlocked());
//
//        if (user.getLocation() != null && user.getLocation().isActive()) {
//            Location activeLocationByLogin = entityManipulator.getActiveLocationByLogin(userByActivationCode.get().getId());
//            System.err.println("activeLocationByLogin: " + activeLocationByLogin);
//            if (activeLocationByLogin != null) {
//                activeLocationByLogin.setActive(false);
//                entityManipulator.updateLocation(activeLocationByLogin);
//            }
//        }
//        if (user.getLocation() != null) user.getLocation().setActive(true);
//        System.err.println(user.getLocation());
//        entityManipulator.insertLocations(user.getLocation());
//
//        Optional<Integer> userCountByLoginAndActivationCode =
//                entityManipulator.getUserCountByLoginAndActivationCode(user.getLogin(), user.getActivationCode());
//        if (userCountByLoginAndActivationCode.isPresent() && userCountByLoginAndActivationCode.get() == 0) {
//            Optional<Integer> userCountByLogin = entityManipulator.getUserCountByLogin(user.getLogin());
//            if (userCountByLogin.isEmpty() || (userCountByLogin.isPresent() && userCountByLogin.get() != 0)) {
//                log.warn("User with login '{}' exist!", user.getActivationCode());
//                return new ResponseError("error", "Поьзователь с логином " + user.getLogin() + " уже существует!");
//            }
//        }
//
//
//        return entityManipulator.updateUserByActivationCode(user);
//    }
//
//    public Object profileSave(ProfileModel profile) {
//
//        Optional<ProfileModel> profileById = entityManipulator.getProfileById(profile.getId());
//        if (profileById.isEmpty()) {
//            log.error("profileSave. Error load profile images '{}'", profile);
//            return new ResponseError("error", "Ошибка! Не удалось сохранить профиль!");
//        }
//
//        if (profileById.get().getImagesIds() != null)
//            profileById.get().getImagesIds().forEach(imageId -> {
//                Optional<Integer> dropRes = entityManipulator.dropImageById(imageId);
//                if (dropRes.isEmpty() || dropRes.get() != 1) {
//                    log.error("profileSave. Error delete image [id = {}]", imageId);
//                }
//            });
//
//        System.err.println("1. " + profile.getImages());
//
//        if (profile.getImages().size() > 0) {
//            profile.getImages().forEach(imageElem -> {
//                Optional<Integer> integer = entityManipulator.insertImage(imageElem);
//                if (integer.isEmpty()) {
//                    log.error("profileSave. Error to save image [{}]", imageElem);
//                } else {
//                    imageElem.setId(integer.get());
//                }
//            });
//            System.err.println("2. " + profile.getImages());
//
//            profile.getImages().stream()
//                    .filter(imageElem -> imageElem.getIndex() == profile.getAvatar())
//                    .findFirst()
//                    .ifPresent(imageElem1 -> profile.setAvatar(imageElem1.getId()));
//            System.err.println("3. " + profile.getImages());
//        }
//
//        Optional<Integer> profileUpdate = entityManipulator.updateProfileById(profile);
//        if (profileUpdate.isEmpty() || profileUpdate.get() != 1) {
//            log.error("profileSave. Error update profile [{}]", profile);
//            return new ResponseError("error", "Ошибка! Не удалось сохранить профиль!!!");
//        }
//
//        return true;
//
//
////        User user = Converter.convertToUser(json);
////        Optional<User> userByLogin = entityManipulator.getUserByLogin(user.getLogin());
////        String message = "";
//
////        entityManipulator.updateProfileById(profile);
//
////                    if (integer.isPresent() && integer.get() == 1) {
////                        //все ок
////                        return true;
////                    } else {
////                        //откат (удаление профиля)
////                        Optional<Integer> integer1 = entityManipulator.dropProfileById(profile1.get());
////                        if (integer1.isPresent() && integer1.get() == 1) {
////                            //откат успешен
////                            message = "User profile create failed. Error update user.";
////                        } else
////                            message = "User profile create failed.";
////                    }
//
//
////        return new ResponseError("error", message);
//    }
//
//    public Object profileGet(String login) {
//
//        MyObject userByLogin = entityManipulator.getUserByLogin(login);
//        if (userByLogin instanceof ResponseError)
//            return userByLogin;
//
//        UserEntity user = (UserEntity) userByLogin;
//        user.setLocation(entityManipulator.getActiveLocationByLogin(user.getId()));
//
//        Optional<ProfileModel> profileById = entityManipulator.getProfileById(user.getProfileId());
//        if (profileById.isEmpty()) {
//            log.error("profileGet. Error load profile for user '{}'", user);
//            return new ResponseError("error", "Ошибка! Не удалось загрузить профиль!");
//        }
//
//        UserAndProfile userAndProfile = new UserAndProfile(user, profileById.get());
//
//        return new ResponseOkData("ok", userAndProfile.toJSONObject()).toString();
//    }
//
//    public List<Location> getLocationList() {
//        return entityManipulator.getLocations();
//    }
//}
