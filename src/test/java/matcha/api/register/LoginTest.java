package matcha.api.register;//import org.junit.jupiter.api.Test;

//import static org.hamcrest.Matchers.equalTo;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import matcha.location.model.Location;
import matcha.user.model.UserInfo;
import matcha.user.model.UserRegistry;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.hamcrest.Matchers.containsString;

@Disabled
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class LoginTest {

    String login = "login";
    String password = "password";

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
//                .setBaseUri("http://localhost")
            .setPort(4567)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
//        .log()
            .build();

    @BeforeAll
    public static void init() throws InterruptedException {
        port = 4567;
        given().when().get("/clear").then().log();
    }

    @Test
    @Order(1)
    public void registerCorrect() {
        Location location = new Location(3.1, 4.1);
        UserRegistry user = new UserRegistry(login, password, "fname1", "lname1", "doqwqqwdqw@mail.ru", location);
        given()
                .spec(requestSpec)
                .body(user)
                .when().post("/register")
                .then().spec(responseSpecOk);
    }

    @Test
    @Order(2)
    public void loginCorrect() {

        Location location = new Location(3.5, 4.7);

        UserInfo userInfo = new UserInfo(login, password, location);

        given()
                .spec(requestSpec)
                .body(userInfo)
                .when().post("/login")
                .then().spec(responseSpecOk);
    }

    @Test
    @Order(2)
    public void loginInorrectLocationNull() {

        Location location = new Location(3.5, 4.7);

        UserInfo userInfo = new UserInfo(login, password, null);

        given()
                .spec(requestSpec)
                .body(userInfo)
                .when().post("/login")
                .then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void loginInorrectLocationXNull() {

        Location location = new Location(null, 4.7);

        UserInfo userInfo = new UserInfo(login, password, location);

        given()
                .spec(requestSpec)
                .body(userInfo)
                .when().post("/login")
                .then().spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void loginInorrectLocationYNull() {

        Location location = new Location(3.5, null);

        UserInfo userInfo = new UserInfo(login, password, location);

        given()
                .spec(requestSpec)
                .body(userInfo)
                .when().post("/login")
                .then().spec(responseSpecError);
    }

}
