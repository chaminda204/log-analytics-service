package au.com.mantel.group.logs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Paths;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = LogAnalyticsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LogAnalyticsIntegrationTest extends BaseIntegrationTest {


    @Test
    void shouldReturnCorrectAnalysisWhenValidLogFileIsAdded() {
        var file = Paths.get("src/test/resources/programming-task-data.log").toFile();

        // @formatter:off
        given()
                .multiPart("file", file)
        .when()
                .post("/api/analytics/logs")
        .then()
                .statusCode(200)
        .and()
                .body("results.noOfUniqueIpAddresses", equalTo(12))
        .and()
                .body("results.mostFrequentIpAddress.size()", equalTo(3))
                .body("results.mostFrequentIpAddress[0].ipAddress", equalTo("168.41.191.40"))
                .body("results.mostFrequentIpAddress[0].occurrences", equalTo(4))
                .body("results.mostFrequentIpAddress[1].ipAddress", equalTo("50.112.00.11"))
                .body("results.mostFrequentIpAddress[1].occurrences", equalTo(3))
                .body("results.mostFrequentIpAddress[2].ipAddress", equalTo("177.71.128.21"))
                .body("results.mostFrequentIpAddress[2].occurrences", equalTo(3))
        .and()
                .body("results.mostVisitedUrls.size()", equalTo(3))
                .body("results.mostVisitedUrls[0].endpoint", equalTo("- "))
                .body("results.mostVisitedUrls[0].occurrences", equalTo(2))
                .body("results.mostVisitedUrls[1].endpoint", equalTo("GET /docs/manage-websites/"))
                .body("results.mostVisitedUrls[1].occurrences", equalTo(2))
                .body("results.mostVisitedUrls[2].endpoint", equalTo("GET /intranet-analytics/"))
                .body("results.mostVisitedUrls[2].occurrences", equalTo(1));

        // @formatter:on
    }


}
