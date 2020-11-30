package matcha.api.register;//import org.junit.jupiter.api.Test;

//import static org.hamcrest.Matchers.equalTo;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import matcha.location.model.Location;
import matcha.user.model.UserRegistry;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@Disabled
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class LoginTest {

    ResponseSpecification responseSpecError = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(200)
            .expectBody("type", containsString("error"))
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

        UserRegistry user = new UserRegistry("login", "password", "fname1",
                "lname1", "doqwqqwdqw@mail.ru", location);

        given()
                .spec(requestSpec)
                .body(user)
                .when().post("/register")
                .then()
                .statusCode(200)
                .log()
//                .extract()
                .body()
//                .as(ResponseOkData.class);
                .body("type", equalTo("ok"));
//    body("type", equalTo("ok"));
        RestAssured.requestSpecification = requestSpec;
    }

    @Test
    public void registerIncorrectLocationIsNull() {

        UserRegistry user = new UserRegistry("login", "password", "fname1",
                "lname1", "doqwqqwdqw@mail.ru", null);

        given()
                .spec(requestSpec)
                .body(user)
                .when().post("/register")
                .then().spec(responseSpecError);
    }

    @Test
    public void registerIncorrectLoginIsEmpty() {

        Location location = new Location(3.1, 4.1);

        UserRegistry user = new UserRegistry("", "password", "fname1",
                "lname1", "doqwqqwdqw@mail.ru", location);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecError);
    }

    @Test
    public void registerIncorrectPasswordIsEmpty() {

        Location location = new Location(3.1, 4.1);

        UserRegistry user = new UserRegistry("login", "", "fname1",
                "lname1", "doqwqqwdqw@mail.ru", location);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecError);
    }

    @Test
    public void registerIncorrectFnameIsEmpty() {

        Location location = new Location(3.1, 4.1);

        UserRegistry user = new UserRegistry("login", "password", "",
                "lname1", "doqwqqwdqw@mail.ru", location);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecError);
    }

    @Test
    public void registerIncorrectLnameIsEmpty() {

        Location location = new Location(3.1, 4.1);

        UserRegistry user = new UserRegistry("login", "password", "fname1",
                "", "doqwqqwdqw@mail.ru", location);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecError);
    }

    @Test
    public void registerIncorrectEmailIsEmpty() {

        Location location = new Location(3.1, 4.1);

        UserRegistry user = new UserRegistry("login", "password", "fname1",
                "lname1", "", location);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecError);
    }

    @Test
    public void registerIncorrectLocationXNull() {

        Location location = new Location(null, 4.1);

        UserRegistry user = new UserRegistry("login", "password", "fname1",
                "lname1", "doqwqqwdqw@mail.ru", location);

        given()
                .spec(requestSpec)
                .body(user)
                .when().post("/register")
                .then().spec(responseSpecError);
    }

    @Test
    public void registerIncorrectLocationYNull() {

        Location location = new Location(3.5, null);

        UserRegistry user = new UserRegistry("login", "password", "fname1",
                "lname1", "doqwqqwdqw@mail.ru", location);

        given()
                .spec(requestSpec)
                .body(user)
                .when().post("/register")
                .then().spec(responseSpecError);
    }

    @Test
    public void registerIncorrectLoginIsNull() {

        Location location = new Location(3.1, 4.1);

        UserRegistry user = new UserRegistry(null, "password", "fname1",
                "lname1", "doqwqqwdqw@mail.ru", location);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecError);
    }

    @Test
    public void registerIncorrectPasswordIsNull() {

        Location location = new Location(3.1, 4.1);

        UserRegistry user = new UserRegistry("login", null, "fname1",
                "lname1", "doqwqqwdqw@mail.ru", location);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecError);
    }

    @Test
    public void registerIncorrectFnameIsNull() {

        Location location = new Location(3.1, 4.1);

        UserRegistry user = new UserRegistry("login", "password", null,
                "lname1", "doqwqqwdqw@mail.ru", location);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecError);
    }

    @Test
    public void registerIncorrectLnameIsNull() {

        Location location = new Location(3.1, 4.1);

        UserRegistry user = new UserRegistry("login", "password", "fname1",
                null, "doqwqqwdqw@mail.ru", location);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecError);
    }

    @Test
    public void registerIncorrectEmailIsNull() {

        Location location = new Location(3.1, 4.1);

        UserRegistry user = new UserRegistry("login", "password", "fname1",
                "lname1", null, location);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecError);
    }

    @Test
    @Order(2)
    public void registerIncorrectLoginExist() {

        Location location = new Location(3.1, 4.1);

        UserRegistry user = new UserRegistry("login", "password", "fname1",
                "lname1", "asdasdDsd@mail.ru", location);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecError);
    }

    @Test
    public void registerIncorrectLoginIsBig() {

        Location location = new Location(3.1, 4.1);

        UserRegistry user = new UserRegistry("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "password", "fname1",
                "lname1", "doqwqqwdqw@mail.ru", location);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecError);
    }

    @Test
    public void registerIncorrectPasswordIsBig() {

        Location location = new Location(3.1, 4.1);

        UserRegistry user = new UserRegistry("login", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@email.ru", "fname1",
                "lname1", "doqwqqwdqw@mail.ru", location);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecError);
    }

    @Test
    public void registerIncorrectFnameIsBig() {

        Location location = new Location(3.1, 4.1);

        UserRegistry user = new UserRegistry("login", "password", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@email.ru",
                "lname1", "doqwqqwdqw@mail.ru", location);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecError);
    }

    @Test
    public void registerIncorrectLnameIsBig() {

        Location location = new Location(3.1, 4.1);

        UserRegistry user = new UserRegistry("login", "password", "fname1",
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@email.ru", "doqwqqwdqw@mail.ru", location);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecError);
    }

    @Test
    public void registerIncorrectEmailIsBig() {

        Location location = new Location(3.1, 4.1);

        UserRegistry user = new UserRegistry("login", "password", "fname1",
                "lname1", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@email.ru", location);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecError);
    }

    @Test
    public void registerIncorrectFieldsEmptyValues() {

        Location location = new Location(3.1, 4.1);

        UserRegistry user = new UserRegistry();

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecError);
    }
}
