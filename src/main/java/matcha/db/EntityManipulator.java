package matcha.db;//package matcha.db;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import matcha.db.crud.Drop;
//import matcha.db.crud.Insert;
//import matcha.db.crud.Select;
//import matcha.db.crud.Update;
//import matcha.image.model.ImageModel;
//import matcha.location.model.Location;
//import matcha.model.MyObject;
//import matcha.model.rowMapper.ImageRowMapper;
//import matcha.model.rowMapper.LocationRowMapper;
//import matcha.model.rowMapper.ProfileRowMapper;
//import matcha.model.rowMapper.UserRowMapper;
//import matcha.profile.model.ProfileModel;
//import matcha.response.ResponseError;
//import matcha.user.model.UserEntity;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.PreparedStatementCreator;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Service;
//
//import java.sql.*;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
////TODO можно логирование переделать под AOP
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class EntityManipulator {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public Optional<Integer> getUserCountByLogin(String login) {
//        return Optional.ofNullable(jdbcTemplate.queryForObject(Select.selectUsersCountByLogin,
//                Integer.class, login));
//    }
//
//    public Optional<Integer> getUserCountByActivationCode(String activationCode) {
//        return Optional.ofNullable(jdbcTemplate.queryForObject(Select.selectUsersCountByActivationCode,
//                Integer.class, activationCode));
//    }
//
//    public Optional<Integer> getUserCountByLoginAndActivationCode(String login, String activationCode) {
//        return Optional.ofNullable(jdbcTemplate.queryForObject(Select.selectUsersCountByLoginAndActivationCode,
//                Integer.class, login, activationCode));
//    }
//
//    //TODO рефакторинг метода
//    public Optional<UserEntity> getUserByActivationCode(String activationCode) {
//        try {
//            UserEntity user = jdbcTemplate.queryForObject(Select.selectUserByActivationCode,
//                    new Object[]{activationCode}, new UserRowMapper());
//            System.err.println("USER TIME: " + user.getTime());
//            log.info("Get user by Activation Code: ".concat(activationCode).concat(" User: ") + user);
//            Optional<UserEntity> userOptional = Optional.ofNullable(user);
//            if (userOptional.isPresent()) {
//                user.setTime(Calendar.getInstance().getTime());
//            }
//            return userOptional;
//        } catch (Exception e) {
//            log.info("getUserByActivationCode. User with activation code {} not fpund", activationCode);
//            return Optional.empty();
//        }
//    }
//
//    public Optional<UserEntity> getUserById(int userId) {
//        UserEntity user = jdbcTemplate.queryForObject(Select.selectUserById, UserEntity.class, userId);
//        log.info("Get user by id: ".concat(String.valueOf(userId)).concat(" User: ") + user);
//        return Optional.ofNullable(user);
//    }
//
//    public MyObject getUserByLogin(String login) {
//        log.info("Get user by login [{}]", login);
//        try {
//            UserEntity user = jdbcTemplate.queryForObject(Select.selectUserByLogin, new UserRowMapper(), login);
//            log.info("Get user by login result: {}", user);
//            return user;
//        } catch (Exception e) {
//            String message = "Пользователь ".concat(login).concat(" не найден");
//            log.info("getUserByLogin. ".concat(message));
//            return new ResponseError("error", message);
//        }
//    }
//
//    public UserEntity getUserByLogin1(String login) {
//        log.info("Get user by login [{}]", login);
//        UserEntity user = jdbcTemplate.queryForObject(Select.selectUserByLogin, new UserRowMapper(), login);
//        log.info("Get user by login result: {}", user);
//        return user;
//    }
//
//    public Object insertUser(UserEntity user) {
//        log.info("Create user: {}", user);
////        int update = jdbcTemplate.update(Insert.insertUser,
////                user.getLogin(), user.getPassword(), user.getActivationCode(), user.getFname(),
////                user.getLname(), user.getEmail(), user.isActive(), user.isBlocked(), user.getTime(),
////                user.getSalt(), user.getProfileId());
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        try {
//            int userId = jdbcTemplate.update(new PreparedStatementCreator() {
//                @Override
//                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                    PreparedStatement ps = connection.prepareStatement(Insert.insertUser, new String[]{"id"});
//                    ps.setString(1, user.getLogin());
//                    ps.setBytes(2, user.getPasswordBytes());
//                    ps.setString(3, user.getActivationCode());
//                    ps.setString(4, user.getFname());
//                    ps.setString(5, user.getLname());
//                    ps.setString(6, user.getEmail());
//                    ps.setBoolean(7, user.isActive());
//                    ps.setBoolean(8, user.isBlocked());
//                    ps.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
//                    ps.setBytes(10, user.getSalt());
//                    ps.setInt(11, user.getProfileId());
//                    return ps;
//                }
//            }, keyHolder);
//            log.info("Create user result: userId = {}", userId);
//            return userId;
//        } catch (Exception e) {
//            log.error("userRegistry. Error create user: {} message:{}", user, e.getMessage());
//            return new ResponseError("error", "Не удалось создать пользователя ".concat(user.getLogin()));
//        }
//    }
//
//    public int insertUser1(UserEntity user) {
//        log.info("Create user: {}", user);
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        int userId = jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                PreparedStatement ps = connection.prepareStatement(Insert.insertUser, new String[]{"id"});
//                ps.setString(1, user.getLogin());
//                ps.setBytes(2, user.getPasswordBytes());
//                ps.setString(3, user.getActivationCode());
//                ps.setString(4, user.getFname());
//                ps.setString(5, user.getLname());
//                ps.setString(6, user.getEmail());
//                ps.setBoolean(7, user.isActive());
//                ps.setBoolean(8, user.isBlocked());
//                ps.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
//                ps.setBytes(10, user.getSalt());
//                ps.setInt(11, user.getProfileId());
//                return ps;
//            }
//        }, keyHolder);
//        log.info("Create user result: userId = {}", userId);
//        return userId;
//    }
//
//    public MyObject updateUserById(UserEntity user) {
//        log.info("Update user: {}", user);
//        try {
//            int update = jdbcTemplate.update(Update.updateUserById,
//                    user.getLogin(), user.getActivationCode(),
//                    user.getFname(), user.getLname(), user.getEmail(),
//                    user.isActive(), user.isBlocked(), user.getTime(), user.getProfileId(), user.getId());
//            log.info("Update user result: ".concat(String.valueOf(update)));
//            return MyObject.stub;
//        } catch (Exception e) {
//            log.warn("updateUserById. Failed update user {}", user);
//            return new ResponseError("error", "");
//        }
//    }
//
//    public MyObject updateUserById1(UserEntity user) {
//        log.info("Update user: {}", user);
//        try {
//            int update = jdbcTemplate.update(Update.updateUserById,
//                    user.getLogin(), user.getActivationCode(),
//                    user.getFname(), user.getLname(), user.getEmail(),
//                    user.isActive(), user.isBlocked(), user.getTime(), user.getProfileId(), user.getId());
//            log.info("Update user result: ".concat(String.valueOf(update)));
//            return MyObject.stub;
//        } catch (Exception e) {
//            log.warn("updateUserById. Failed update user {}", user);
//            return new ResponseError("error", "");
//        }
//    }
//
//    public Optional<Integer> updateUserByActivationCode(UserEntity user) {
//        log.info("Update user: ".concat(user.toString()));
//        int update = jdbcTemplate.update(Update.updateUserByActivationCode,
//                user.getLogin(),
//                user.getFname(), user.getLname(), user.getEmail(),
//                user.isActive(), user.isBlocked(), user.getTime(), user.getActivationCode());
//        log.info("Update user result: ".concat(String.valueOf(update)));
//        return Optional.of(update);
//    }
//
//    public Optional<Integer> dropUserById(String id) {
//        log.info("Drop user by id: ".concat(id));
//        int drop = jdbcTemplate.update(Drop.deleteUserById, id);
//        log.info("Drop user by login result: ".concat(String.valueOf(drop)));
//        return Optional.of(drop);
//    }
//
//    public Optional<Integer> dropUserByLogin(String login) {
//        log.info("Drop user by login: ".concat(login));
//        int drop = jdbcTemplate.update(Drop.deleteUserById, login);
//        log.info("Drop user by login result: ".concat(String.valueOf(drop)));
//        return Optional.of(drop);
//    }
//
//    /**************************************************************************************************************/
//
//    public Optional<Integer> getProfileCountById(int profileId) {
//        Integer integer = jdbcTemplate.queryForObject(Select.selectProfilesCountById,
//                Integer.class, profileId);
//        return Optional.ofNullable(integer);
//    }
//
//    //TODO рефакторинг метода
//    public Optional<ProfileModel> getProfileById(int profileId) {
//
//        ProfileModel profile = jdbcTemplate.queryForObject(Select.selectProfileById,
//                new ProfileRowMapper(), profileId);
//        log.info("Get profile by id: ".concat(String.valueOf(profileId)).concat(" Profile: ") + profile);
//
//        if (profile.getImagesIds() != null && profile.getImagesIds().size() > 0) {
//            profile.getImagesIds().forEach(s -> {
//                Optional<ImageModel> imageById = getImageById(s);
//                if (imageById.isPresent()) {
//                    profile.getImages().add(imageById.get());
//                } else {
//                    log.warn("getProfileById. Fail to get image id:{}", s);
//                }
//            });
//            //            profile.setImages(profile.getImagesIds().stream()
////                    .map(this::getImageById).map(Optional::get).collect(Collectors.toList()));
//        }
//        return Optional.of(profile);
//    }
//
//    public Optional<Integer> insertEmptyProfile() {
//        log.info("Create empty profile");
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        int update = jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                PreparedStatement ps = connection.prepareStatement(Insert.insertProfile, new String[]{"id"});
//                ps.setNull(1, Types.INTEGER);
//                ps.setNull(2, Types.INTEGER);
//                ps.setNull(3, Types.VARCHAR);
//                ps.setNull(4, Types.VARCHAR);
//                ps.setNull(5, Types.VARCHAR);
//                ps.setNull(6, Types.VARCHAR);
//                ps.setNull(7, Types.VARCHAR);
//                return ps;
//            }
//        }, keyHolder);
//        log.info("Create empty profile result: ".concat(String.valueOf(keyHolder.getKey())));
//        if (update != 1)
//            return Optional.empty();
//        return Optional.of(keyHolder.getKey().intValue());
//    }
//
////    public Optional<Integer> createProfile(Profile profile) {
////        log.info("Create profile: ".concat(profile.toString()));
////        KeyHolder keyHolder = new GeneratedKeyHolder();
////        int update = jdbcTemplate.update(new PreparedStatementCreator() {
////            @Override
////            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
////                PreparedStatement ps = connection.prepareStatement(Insert.insertProfile, new String[]{"id"});
////                ps.setInt(1, profile.getAge());
////                ps.setInt(2, profile.getGender());
////                ps.setString(3, profile.getPreference().stream()
////                        .map(String::valueOf)
////                        .collect(Collectors.joining(",")));
////                ps.setString(4, profile.getBiography());
////                ps.setString(5, String.join(",", profile.getTags()));
//////TODO изменить работу с изображением
//////                ps.setString(6, String.join(",", profile.getImages()));
////                ps.setInt(7, profile.getAvatar());
////                return ps;
////            }
////        }, keyHolder);
////        log.info("Create profile result: ".concat(String.valueOf(keyHolder.getKey())));
////        if (update != 1)
////            return Optional.empty();
////        return Optional.of(keyHolder.getKey().intValue());
////    }
//
//    public Optional<Integer> updateProfileById(ProfileModel profile) {
//        log.info("Update profile: {}", profile);
//
////TODO аватар доделать
//        String imagesIds = profile.getImages().stream()
//                .map(imageElem -> String.valueOf(imageElem.getId()))
//                .collect(Collectors.joining(","));
//
//        String preference = null;
//        if (profile.getPreferenceAsString().size() > 0)
//            preference = profile.getPreferenceAsString().stream()
//                    .map(String::valueOf)
//                    .collect(Collectors.joining(","));
//
//        int update = jdbcTemplate.update(Update.updateProfileById,
//                profile.getAge(), profile.getGender(), preference, profile.getBiography(),
//                String.join(",", profile.getTagsAsString()), imagesIds, profile.getAvatar(), profile.getId());
//        log.info("Update profile result: {}", update);
//        return Optional.of(update);
//    }
//
//    public Optional<Integer> dropProfileById(int id) {
//        log.info("Drop user by id: ".concat(String.valueOf(id)));
//        int drop = jdbcTemplate.update(Drop.deleteProfileById, id);
//        log.info("Drop user by id result: ".concat(String.valueOf(drop)));
//        return Optional.of(drop);
//    }
//
//    /**************************************************************************************************************/
//
//    public Optional<Integer> getImageCountById(int imageId) {
//        Integer integer = jdbcTemplate.queryForObject(Select.selectImagesCountById,
//                Integer.class, imageId);
//        return Optional.ofNullable(integer);
//    }
//
//    public Optional<ImageModel> getImageById(String imageId) {
//        ImageModel image = jdbcTemplate.queryForObject(Select.selectImageById, new ImageRowMapper(), imageId);
//        log.info("Get image by id: ".concat(imageId).concat(" Image: ") + image);
//        return Optional.ofNullable(image);
//    }
//
//    public List<Location> getLocations() {
//        log.info("Get all locations");
//        try {
//            List<Location> query = jdbcTemplate.query(Select.selectLocations, new LocationRowMapper());
//            log.info("Get all locations. Count: {}", query.size());
//            return query;
//        } catch (Exception e) {
//            log.warn("Failed to load all locations");
//            return null;
//        }
//    }
//
//    public Location getActiveLocationByLogin(Integer userId) {
//        log.info("Get active location by login {}...", userId);
//        try {
//            Location query = jdbcTemplate.queryForObject(Select.selectLocationByUserIdAndActive,
//                    new LocationRowMapper(), userId);
//            log.info("Get active location by login done. Result: {}", query);
//            return query;
//        } catch (Exception e) {
//            log.info("Failed to load active location for user {}", userId);
//            return null;
//        }
//    }
//
//    public Integer updateLocation(Location location) {
//        log.info("Update location {}...", location);
//        try {
//            int update = jdbcTemplate.update(Update.updateLocationById, location.isActive(), location.getId());
//            log.info("Update location done. Result: {}", update);
//            return update;
//        } catch (Exception e) {
//            log.info("Failed to update location {}", location);
//            return null;
//        }
//    }
//
////    public Optional<Integer> createImage(Image image) {
////        log.info("Create image: ".concat(image.toString()));
////        int update = jdbcTemplate.update(Insert.insertImage, image.getImg());
////        log.info("Create image result: ".concat(String.valueOf(update)));
////        return Optional.of(update);
////    }
//
//    public Optional<Integer> insertImage(ImageModel image) {
//        log.info("Insert image '{}'", image);
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        int update = jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                PreparedStatement ps = connection.prepareStatement(Insert.insertImage, new String[]{"id"});
//                ps.setInt(1, image.getIndex());
//                ps.setString(2, image.getSrc());
//                return ps;
//            }
//        }, keyHolder);
//        log.info("Insert image result: ".concat(String.valueOf(keyHolder.getKey())));
//        if (update != 1)
//            return Optional.empty();
//        return Optional.of(keyHolder.getKey().intValue());
//    }
//
//    public void insertLocations(Location location) {
//        log.info("Insert location '{}'", location);
//        if (location == null) return;
//        try {
//            System.err.println("TIME: " + location.getTime());
//            int insert = jdbcTemplate.update(Insert.insertLocation, location.getUser(),
//                    location.getX(), location.getY(), location.getTime(), location.isActive());
//            log.info("Insert image result: {}", insert);
//        } catch (Exception e) {
//            log.error("insertLocations. Failed to insert location:[{}] message:{}", location, e.getMessage());
//        }
//    }
//
//    public void insertLocation1(Location location) {
//        log.info("Insert location '{}'", location);
//        int insert = jdbcTemplate.update(Insert.insertLocation, location.getUser(),
//                location.getX(), location.getY(), location.getTime(), location.isActive());
//        log.info("Insert image result: {}", insert);
//    }
//
//    public Optional<Integer> updateImageById(ImageModel image) {
//        log.info("Update image: ".concat(image.toString()));
//        int update = jdbcTemplate.update(Update.updateImageById, image.getIndex(), image.getSrc(), image.getId());
//        log.info("Update image result: ".concat(String.valueOf(update)));
//        return Optional.of(update);
//    }
//
//    public Optional<Integer> dropImageById(String id) {
//        log.info("Drop image by id: ".concat(id));
//        int drop = jdbcTemplate.update(Drop.deleteImageById, id);
//        log.info("Drop image by id result: ".concat(String.valueOf(drop)));
//        return Optional.of(drop);
//    }
//}
