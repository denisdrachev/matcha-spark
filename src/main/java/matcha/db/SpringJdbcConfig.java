package matcha.db;

import matcha.image.model.Image;
import matcha.location.model.Location;
import matcha.profile.service.ProfileService;
import matcha.properties.ConfigProperties;
import matcha.user.model.UserEntity;
import matcha.user.model.UserRegistry;
import matcha.user.service.UserService;
import matcha.userprofile.model.UserInfoModel;

import java.util.List;

//@Configuration
//@AllArgsConstructor
public class SpringJdbcConfig {

    ConfigProperties properties;
    UserService userService = UserService.getInstance();
    ProfileService profileService = ProfileService.getInstance();

    public SpringJdbcConfig() {
        createAllTables();
    }

    //    @Bean
    public void createAllTables() {

//        Drop.getAll().stream().forEach(s -> jdbcTemplate.execute(s));

//        Properties.getAllTablesPath().stream().forEach(s -> createTableBySql(s));

//        createUsers(500);

//        createUser();
//        createUser2();
//        createUser3();

//        jdbcTemplate.update(Insert.insertImage, 0, "ABCD");
//        jdbcTemplate.update(Insert.insertUser, "loginnnn", "password".getBytes(), null, "Artur", "Kamnev", "fermer@gmail.com", 1, 0, new Date(), "salt_test".getBytes(), null);
//        jdbcTemplate.update(Insert.insertProfile, 22, 1, 0, "Simple fermer", "fermer", null, 1);
//        jdbcTemplate.update(Insert.insertLocation, 1, 55.6634545, 37.5102081, Calendar.getInstance().getTime(), false);
//        jdbcTemplate.update(Insert.insertRaiting, 7, 1);
//        jdbcTemplate.update(Insert.insertBlacklist, 1, 1);
//        jdbcTemplate.update(Insert.insertImageLikeEvent, 1, 1, 1, 1);

//        jdbcTemplate.query(Select.selectImage, new BeanPropertyRowMapper(ImageModel.class)).forEach(System.out::println);
//        jdbcTemplate.query(Select.selectProfile, new BeanPropertyRowMapper(ProfileModel.class)).forEach(System.out::println);
    }


    private void createUser0() {
        String value = "-1";
        Location location = new Location();
        location.setX(1.1);
        location.setY(1.1);
        UserRegistry userRegistry = new UserRegistry(
                "test3", "123", "fname_test3", "lname_test3", value + "@mail.ru", location
        );
        userService.userRegistration(userRegistry);


        UserEntity user_1 = userService.getUserByLogin("test3");
        System.err.println(user_1.getActivationCode());

        UserInfoModel userInfo = new UserInfoModel();
        userInfo.setLocation(user_1.getLocation());
        userInfo.setEmail(user_1.getEmail());
        userInfo.setLname(user_1.getLname());
        userInfo.setFname(user_1.getFname());
//        ProfileEntity profileByIdWithImages = profileService.getProfileByIdWithImages(user_1.getProfileId());
        userInfo.setAge(22);
        userInfo.setGender(1);
        userInfo.setPreference(3);
        userInfo.setTags(List.of("tag2", "tag3"));
        userInfo.setBiography("asdasdasd");

        Image image = new Image();
        image.setAvatar(true);
        image.setSrc("https://i.ibb.co/cw9TnX9/0f6339673fed.jpg");
        image.setIndex(1);

        userInfo.setImages(List.of(image));

        userService.saveUserInfo(userInfo, user_1.getLogin());

//        profileService.updateProfile(profileByIdWithImages.getId(), profileByIdWithImages);
    }

    private void createUser() {
        String value = "-1";
        Location location = new Location();
        location.setX(2.2);
        location.setY(2.2);
        UserRegistry userRegistry = new UserRegistry(
                "User_1", "123", "fname", "lname", value + "@mail.ru", location
        );
        userService.userRegistration(userRegistry);


        UserEntity user_1 = userService.getUserByLogin("User_1");
        System.err.println(user_1.getActivationCode());

        UserInfoModel userInfo = new UserInfoModel();
        userInfo.setLocation(user_1.getLocation());
        userInfo.setEmail(user_1.getEmail());
        userInfo.setLname(user_1.getLname());
        userInfo.setFname(user_1.getFname());
//        ProfileEntity profileByIdWithImages = profileService.getProfileByIdWithImages(user_1.getProfileId());
        userInfo.setAge(22);
        userInfo.setGender(1);
        userInfo.setPreference(3);
        userInfo.setTags(List.of("tag1", "tag2", "tag3"));
        userInfo.setBiography("asdasdasd");

        Image image = new Image();
        image.setAvatar(true);
        image.setSrc("https://i.ibb.co/cw9TnX9/0f6339673fed.jpg");
        image.setIndex(1);

        userInfo.setImages(List.of(image));

        userService.saveUserInfo(userInfo, user_1.getLogin());

//        profileService.updateProfile(profileByIdWithImages.getId(), profileByIdWithImages);
    }

    private void createUser2() {
        String value = "-1";
        Location location = new Location();
        location.setX(1.1);
        location.setY(1.1);
        UserRegistry userRegistry = new UserRegistry(
                "User_2", "123", "fname", "lname", value + "@mail.ru", location
        );
        userService.userRegistration(userRegistry);


        UserEntity user_1 = userService.getUserByLogin("User_2");
        System.err.println(user_1.getActivationCode());

        UserInfoModel userInfo = new UserInfoModel();
        userInfo.setLocation(user_1.getLocation());
        userInfo.setEmail(user_1.getEmail());
        userInfo.setLname(user_1.getLname());
        userInfo.setFname(user_1.getFname());
//        ProfileEntity profileByIdWithImages = profileService.getProfileByIdWithImages(user_1.getProfileId());
        userInfo.setAge(40);
        userInfo.setGender(2);
        userInfo.setPreference(2);
        userInfo.setTags(List.of("tag2", "tag3", "tag4"));
        userInfo.setBiography("asdasdasd");

        Image image = new Image();
        image.setAvatar(true);
        image.setSrc("https://i.ibb.co/cw9TnX9/0f6339673fed.jpg");
        image.setIndex(1);

        userInfo.setImages(List.of(image));

        userService.saveUserInfo(userInfo, user_1.getLogin());

//        profileService.updateProfile(profileByIdWithImages.getId(), profileByIdWithImages);
    }

    private void createUser3() {
        String value = "-1";
        Location location = new Location();
        location.setX(1.1);
        location.setY(1.1);
        UserRegistry userRegistry = new UserRegistry(
                "User_3", "123", "fname", "lname", value + "@mail.ru", location
        );
        userService.userRegistration(userRegistry);


        UserEntity user_1 = userService.getUserByLogin("User_3");
        System.err.println(user_1.getActivationCode());

        UserInfoModel userInfo = new UserInfoModel();
        userInfo.setLocation(user_1.getLocation());
        userInfo.setEmail(user_1.getEmail());
        userInfo.setLname(user_1.getLname());
        userInfo.setFname(user_1.getFname());
//        ProfileEntity profileByIdWithImages = profileService.getProfileByIdWithImages(user_1.getProfileId());
        userInfo.setAge(22);
        userInfo.setGender(1);
        userInfo.setPreference(3);
        userInfo.setTags(List.of("tag4", "tag5"));
        userInfo.setBiography("asdasdasd");

        Image image = new Image();
        image.setAvatar(true);
        image.setSrc("https://i.ibb.co/cw9TnX9/0f6339673fed.jpg");
        image.setIndex(1);

        userInfo.setImages(List.of(image));

        userService.saveUserInfo(userInfo, user_1.getLogin());

//        profileService.updateProfile(profileByIdWithImages.getId(), profileByIdWithImages);
    }

    private void createUsers(Integer usersCount) {
        for (int i = 0; i < usersCount; i++) {
            String value = String.valueOf(i);
            Location location = new Location();
            location.setX(1.1);
            location.setY(1.1);
            UserRegistry userRegistry = new UserRegistry(
                    value, value, value, value, value + "@mail.ru", location
            );
            userService.userRegistration(userRegistry);
        }
    }

    public void preDestroy() {
//        try {
//            System.out.println("CONNECTION!");
//            dataSource.getConnection().close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

//    private void createTableBySql(String pathToSql) {
//        try {
//            String content = Files.readString(Paths.get(pathToSql));
//            jdbcTemplate.execute(content);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

