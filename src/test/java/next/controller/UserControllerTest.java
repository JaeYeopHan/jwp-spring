package next.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import next.config.AppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import static io.restassured.RestAssured.given;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class UserControllerTest {
    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        RestAssured.port = 8080;
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "USERS");
    }

}
