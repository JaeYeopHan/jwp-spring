package next.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class UserRAControllerTest {
    private static final Logger log = LoggerFactory.getLogger(UserRAControllerTest.class);

    @Before
    public void setup() {
        RestAssured.port = 8080;
    }

    @Test
    public void 회원목록() {
        String body =
            given()
                .auth().preemptive().basic("admin", "password")
                .contentType(ContentType.HTML)
            .when()
                .get("/questions/new")
            .then()
                .statusCode(200)
                .extract()
                .asString();
        log.debug("body : {}", body);
    }
}
