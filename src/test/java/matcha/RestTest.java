package matcha;//import org.junit.jupiter.api.Test;

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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@Disabled
public class RestTest {

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

        UserRegistry user = new UserRegistry("", "password", "fname1",
                "lname1", "doqwqqwdqw@mail.ru", null);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecError);
    }
}
