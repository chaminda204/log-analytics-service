package au.com.mantel.group.logs;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(classes = LogAnalyticsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIntegrationTest {

    private static final String BASE_URL = "http://localhost";

    @LocalServerPort
    private int port;

    RequestSpecification given() {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .port(port);
    }

    @BeforeEach
    void beforeEachTest() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
