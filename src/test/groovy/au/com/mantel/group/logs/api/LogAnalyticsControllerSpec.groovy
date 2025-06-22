package au.com.mantel.group.logs.api

import au.com.mantel.group.logs.LogAnalyticsFixtures
import au.com.mantel.group.logs.service.LogAnalyticsService
import org.springframework.http.codec.multipart.FilePart
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification

class LogAnalyticsControllerSpec extends Specification implements LogAnalyticsFixtures {

    def logAnalyticsService = Mock(LogAnalyticsService)

    def logAnalyticsController = new LogAnalyticsController(logAnalyticsService)

    def 'should accept logfile and perform analysis'() {
        given:
        def file = Mock(FilePart)
        def logAnalyticsResp = logAnalyticsResponse()

        when:
        var response = logAnalyticsController.analyse(file)

        then:
        StepVerifier.create(response)
                .expectNext(logAnalyticsResp)
                .verifyComplete()
        1 * logAnalyticsService.analyse(file) >> Mono.just(logAnalyticsResp)
    }
}
