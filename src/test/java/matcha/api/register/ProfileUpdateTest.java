package matcha.api.register;//import org.junit.jupiter.api.Test;

//import static org.hamcrest.Matchers.equalTo;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.slf4j.Slf4j;
import matcha.image.model.Image;
import matcha.location.model.LocationLight;
import matcha.user.model.UserInfo;
import matcha.user.model.UserRegistry;
import matcha.userprofile.model.UserInfoModel;
import org.junit.jupiter.api.*;
import spark.utils.Assert;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.hamcrest.Matchers.containsString;

@Slf4j
@Disabled
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class ProfileUpdateTest {

    String login = "login";
    String password = "password";
    static String token = "";

    ResponseSpecification responseSpecError = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(200)
            .expectBody("type", containsString("error"))
            .log(LogDetail.BODY)
            .build();
    ResponseSpecification responseSpecOk = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(200)
            .expectBody("type", containsString("ok"))
            .log(LogDetail.BODY)
            .build();

    RequestSpecification requestSpec = new RequestSpecBuilder()
            .setPort(4567)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .build().header("Authorization", token);

    @BeforeAll
    public static void init() throws InterruptedException {
        port = 4567;
        given().when().get("/clear").then().log();
    }

    @Test
    @Order(1)
    public void registerAndLoginCorrect() {
        log.info("registerAndLoginCorrect");
        LocationLight location = new LocationLight(3.1, 4.1);
        UserRegistry user = new UserRegistry(login, password, "fname1", "lname1", "doqwqqwdqw@mail.ru", location);
        given()
                .spec(requestSpec)
                .body(user)
                .when().post("/register")
                .then().spec(responseSpecOk);

        UserInfo userInfo = new UserInfo(login, password, location);

        token = given()
                .spec(requestSpec)
                .body(userInfo)
                .when().post("/login")
                .then().spec(responseSpecOk)
                .extract().path("token");

        Assert.notNull(token, "Token is NULL!");
        System.out.println("token: " + token);
    }

    @Test
    @Order(2)
    public void profileUpdateСorrectLocationLightNull() {
        log.info("profileUpdateСorrectLocationLightNull");

        LocationLight location = new LocationLight(3.5, 4.7);

        Image image = new Image(0, "src:doqkwod", 1, true);

        UserInfoModel userInfoModel = new UserInfoModel("fname3", "lname3", "dqwdqwdqwdqwd@mail.ru",
                22, 1, 3, "dasdasd", List.of("tag1", "tag2"), List.of(image), null);

        given()
                .spec(requestSpec)
                .body(userInfoModel)
                .when().post("/profile-update")
                .then().spec(responseSpecOk);
    }

    @Test
    @Order(2)
    public void profileUpdateСorrectLocationLightNotNull() {
        log.info("profileUpdateСorrectLocationLightNotNull");

        LocationLight location = new LocationLight(3.5, 4.7);

        Image image = new Image(0, "src:doqkwod", 1, true);

        UserInfoModel userInfoModel = new UserInfoModel("fname3", "lname3", "dqwdqwdqwdqwd@mail.ru",
                22, 1, 3, "dasdasd", List.of("tag1", "tag2"), List.of(image), location);

        given()
                .spec(requestSpec)
                .body(userInfoModel)
                .when().post("/profile-update")
                .then().spec(responseSpecOk);
    }

    @Test
    @Order(2)
    public void profileUpdateCorrectImageSize5() {
        log.info("profileUpdateCorrectImageSize5");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        Image image2 = new Image(1, "src:doqkwod", 1, false);
        Image image3 = new Image(2, "src:doqkwod", 1, false);
        Image image4 = new Image(3, "src:doqkwod", 1, false);
        Image image5 = new Image(4, "src:doqkwod", 1, false);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "dasd", List.of("tag2"), List.of(image, image2, image3, image4, image5), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecOk);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectLnameEmpty() {
        log.info("profileUpdateIncorrectLnameEmpty");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fname3", "", "dqwdqwdqwdqwd@mail.ru",
                22, 1, 3, "dasdasd", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectLnameNull() {
        log.info("profileUpdateIncorrectLnameNull");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fname3", null, "dqwdqwdqwdqwd@mail.ru",
                22, 1, 3, "dasdasd", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectLnameSize() {
        log.info("profileUpdateIncorrectLnameSize");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fname",
                "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", "dqwdqwdqwdqwd@mail.ru",
                22, 1, 3, "dasdasd", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectFnameEmpty() {
        log.info("profileUpdateIncorrectFnameEmpty");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("", "lname3", "dqwdqwdqwdqwd@mail.ru",
                22, 1, 3, "dasdasd", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectFnameNull() {
        log.info("profileUpdateIncorrectFnameNull");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel(null, "lname3", "dqwdqwdqwdqwd@mail.ru",
                22, 1, 3, "dasdasd", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectFnameSize() {
        log.info("profileUpdateIncorrectFnameSize");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff",
                "lname3", "dqwdqwdqwdqwd@mail.ru",
                22, 1, 3, "dasdasd", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }


    @Test
    @Order(2)
    public void profileUpdateIncorrectEmailEmpty() {
        log.info("profileUpdateIncorrectEmailEmpty");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fname3", "lname3", "",
                22, 1, 3, "dasdasd", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectEmailNull() {
        log.info("profileUpdateIncorrectEmailNull");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", null,
                22, 1, 3, "dasdasd", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectEmailNotPattern() {
        log.info("profileUpdateIncorrectEmailNotPattern");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq",
                22, 1, 3, "dasdasd", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectEmailErrorLength() {
        log.info("profileUpdateIncorrectEmailErrorLength");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3",
                "dqwdqwdqwdqdqwdqwdqwdqdqwdqwdqwdqdqwdqwdqwdqdqwdqwdqwdqdqwdqwdqwdqdqwdqwdqwdqdqwdqwdqwdqdqwdqwdqwdqdqwdqwdqwdqdqwdqwdqwdqdqwdqwdqwdqdqwdqwdqwdqdqwdqwdqwdqdqwdqwdqwdqdqwdqwdqwdqdqwdqwdqwdq@mail.ru",
                22, 1, 3, "dasdasd", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectAgeBelowZero() {
        log.info("profileUpdateIncorrectAgeBelowZero");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                -4, 1, 3, "dasdasd", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectAgeMore120() {
        log.info("profileUpdateIncorrectAgeMore120");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                121, 1, 3, "dasdasd", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectGenderValue0() {
        log.info("profileUpdateIncorrectGenderValue0");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 0, 3, "dasdasd", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectGenderValue4() {
        log.info("profileUpdateIncorrectGenderValue4");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 4, 3, "dasdasd", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectGenderValueNull() {
        log.info("profileUpdateIncorrectGenderValueNull");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, null, 3, "dasdasd", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectPreferenceValue0() {
        log.info("profileUpdateIncorrectPreferenceValue0");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 0, "dasdasd", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectPreferenceValue4() {
        log.info("profileUpdateIncorrectPreferenceValue4");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 4, "dasdasd", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectPreferenceValueNull() {
        log.info("profileUpdateIncorrectPreferenceValueNull");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, null, "dasdasd", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectBiographyNull() {
        log.info("profileUpdateIncorrectBiographyNull");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, null, List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectBiographyEmpty() {
        log.info("profileUpdateIncorrectBiographyEmpty");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectBiographyValueBigLength() {
        log.info("profileUpdateIncorrectBiographyValueBigLength");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3,
                "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii", List.of("tag1", "tag2"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectTagsNull() {
        log.info("profileUpdateIncorrectTagsNull");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "dasd", null, List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectTagsEmpty() {
        log.info("profileUpdateIncorrectTagsEmpty");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "dasd", List.of(), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectTagsCount11() {
        log.info("profileUpdateIncorrectTagsCount11");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "dasd", List.of("q", "q", "q", "q", "q", "q", "q", "q", "q", "q", "q"), List.of(image), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectImageEmpty() {
        log.info("profileUpdateIncorrectImageEmpty");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "dasd", List.of("tag2"), List.of(), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectImageNull() {
        log.info("profileUpdateIncorrectImageNull");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "dasd", List.of("tag2"), null, location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectImageSize6() {
        log.info("profileUpdateIncorrectImageSize6");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "src:doqkwod", 1, true);
        Image image2 = new Image(1, "src:doqkwod", 1, false);
        Image image3 = new Image(2, "src:doqkwod", 1, false);
        Image image4 = new Image(3, "src:doqkwod", 1, false);
        Image image5 = new Image(4, "src:doqkwod", 1, false);
        Image image6 = new Image(5, "src:doqkwod", 1, false);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "dasd", List.of("tag2"), List.of(image, image2, image3, image4, image5, image6), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectImageAvatarSrcEmpty() {
        log.info("profileUpdateIncorrectImageAvatarSrcEmpty");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "", 1, true);
        Image image2 = new Image(1, "src:doqkwod", 1, false);
        Image image3 = new Image(2, "src:doqkwod", 1, false);
        Image image4 = new Image(3, "src:doqkwod", 1, false);
        Image image5 = new Image(4, "src:doqkwod", 1, false);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "dasd", List.of("tag2"), List.of(image, image2, image3, image4, image5), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectImageNonAvatar() {
        log.info("profileUpdateIncorrectImageNonAvatar");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "daqwdqwdq", 1, false);
        Image image2 = new Image(1, "src:doqkwod", 1, false);
        Image image3 = new Image(2, "src:doqkwod", 1, false);
        Image image4 = new Image(3, "src:doqkwod", 1, false);
        Image image5 = new Image(4, "src:doqkwod", 1, false);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "dasd", List.of("tag2"), List.of(image, image2, image3, image4, image5), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectImageDoubleAvatar() {
        log.info("profileUpdateIncorrectImageDoubleAvatar");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "daqwdqwdq", 1, true);
        Image image2 = new Image(1, "src:doqkwod", 1, true);
        Image image3 = new Image(2, "src:doqkwod", 1, false);
        Image image4 = new Image(3, "src:doqkwod", 1, false);
        Image image5 = new Image(4, "src:doqkwod", 1, false);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "dasd", List.of("tag2"), List.of(image, image2, image3, image4, image5), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectImageIndexLessThen0() {
        log.info("profileUpdateIncorrectImageIndexLessThen0");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "daqwdqwdq", -1, true);
        Image image2 = new Image(-1, "src:doqkwod", 1, false);
        Image image3 = new Image(2, "src:doqkwod", 1, false);
        Image image4 = new Image(3, "src:doqkwod", 1, false);
        Image image5 = new Image(4, "src:doqkwod", 1, false);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "dasd", List.of("tag2"), List.of(image, image2, image3, image4, image5), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectImageIndexMoreThen4() {
        log.info("profileUpdateIncorrectImageIndexMoreThen4");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "daqwdqwdq", -1, true);
        Image image2 = new Image(1, "src:doqkwod", 1, false);
        Image image3 = new Image(5, "src:doqkwod", 1, false);
        Image image4 = new Image(3, "src:doqkwod", 1, false);
        Image image5 = new Image(4, "src:doqkwod", 1, false);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "dasd", List.of("tag2"), List.of(image, image2, image3, image4, image5), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectImageIndexNotUniq() {
        log.info("profileUpdateIncorrectImageIndexNotUniq");
        LocationLight location = new LocationLight(3.5, 4.7);
        Image image = new Image(0, "daqwdqwdq", -1, true);
        Image image2 = new Image(0, "src:doqkwod", 1, false);
        Image image3 = new Image(2, "src:doqkwod", 1, false);
        Image image4 = new Image(3, "src:doqkwod", 1, false);
        Image image5 = new Image(4, "src:doqkwod", 1, false);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "dasd", List.of("tag2"), List.of(image, image2, image3, image4, image5), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectLocationXNull() {
        log.info("profileUpdateIncorrectLocationXNull");
        LocationLight location = new LocationLight(null, 4.7);
        Image image = new Image(0, "daqwdqwdq", -1, true);
        Image image2 = new Image(1, "src:doqkwod", 1, false);
        Image image3 = new Image(2, "src:doqkwod", 1, false);
        Image image4 = new Image(3, "src:doqkwod", 1, false);
        Image image5 = new Image(4, "src:doqkwod", 1, false);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "dasd", List.of("tag2"), List.of(image, image2, image3, image4, image5), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectLocationYNull() {
        log.info("profileUpdateIncorrectLocationYNull");
        LocationLight location = new LocationLight(5.1, null);
        Image image = new Image(0, "daqwdqwdq", -1, true);
        Image image2 = new Image(1, "src:doqkwod", 1, false);
        Image image3 = new Image(2, "src:doqkwod", 1, false);
        Image image4 = new Image(3, "src:doqkwod", 1, false);
        Image image5 = new Image(4, "src:doqkwod", 1, false);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "dasd", List.of("tag2"), List.of(image, image2, image3, image4, image5), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectLocationYLess180() {
        log.info("profileUpdateIncorrectLocationYNull");
        LocationLight location = new LocationLight(5.1, -200D);
        Image image = new Image(0, "daqwdqwdq", -1, true);
        Image image2 = new Image(1, "src:doqkwod", 1, false);
        Image image3 = new Image(2, "src:doqkwod", 1, false);
        Image image4 = new Image(3, "src:doqkwod", 1, false);
        Image image5 = new Image(4, "src:doqkwod", 1, false);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "dasd", List.of("tag2"), List.of(image, image2, image3, image4, image5), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectLocationYMore180() {
        log.info("profileUpdateIncorrectLocationYNull");
        LocationLight location = new LocationLight(5.1, 200D);
        Image image = new Image(0, "daqwdqwdq", -1, true);
        Image image2 = new Image(1, "src:doqkwod", 1, false);
        Image image3 = new Image(2, "src:doqkwod", 1, false);
        Image image4 = new Image(3, "src:doqkwod", 1, false);
        Image image5 = new Image(4, "src:doqkwod", 1, false);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "dasd", List.of("tag2"), List.of(image, image2, image3, image4, image5), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectLocationXLess180() {
        log.info("profileUpdateIncorrectLocationYNull");
        LocationLight location = new LocationLight(200D, -20D);
        Image image = new Image(0, "daqwdqwdq", -1, true);
        Image image2 = new Image(1, "src:doqkwod", 1, false);
        Image image3 = new Image(2, "src:doqkwod", 1, false);
        Image image4 = new Image(3, "src:doqkwod", 1, false);
        Image image5 = new Image(4, "src:doqkwod", 1, false);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "dasd", List.of("tag2"), List.of(image, image2, image3, image4, image5), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void profileUpdateIncorrectLocationXMore180() {
        log.info("profileUpdateIncorrectLocationYNull");
        LocationLight location = new LocationLight(-200D, 20D);
        Image image = new Image(0, "daqwdqwdq", -1, true);
        Image image2 = new Image(1, "src:doqkwod", 1, false);
        Image image3 = new Image(2, "src:doqkwod", 1, false);
        Image image4 = new Image(3, "src:doqkwod", 1, false);
        Image image5 = new Image(4, "src:doqkwod", 1, false);
        UserInfoModel userInfoModel = new UserInfoModel("fnam3", "lname3", "dqwdqwdqwdq@mail.ru",
                55, 2, 3, "dasd", List.of("tag2"), List.of(image, image2, image3, image4, image5), location);
        given().spec(requestSpec).body(userInfoModel).when().post("/profile-update").then().spec(responseSpecError);
    }
}
