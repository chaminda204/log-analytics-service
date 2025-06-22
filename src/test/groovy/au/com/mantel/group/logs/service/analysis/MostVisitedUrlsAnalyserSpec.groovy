package au.com.mantel.group.logs.service.analysis

import au.com.mantel.group.logs.LogAnalyticsFixtures
import spock.lang.Specification

class MostVisitedUrlsAnalyserSpec extends Specification implements LogAnalyticsFixtures {
    def mostVisitedUrlAnalyser = new MostVisitedUrlsAnalyser()

    def 'should analyse log entries and return most visited urls'() {
        given:
        def logEntries = logEntries()

        when:
        def response = mostVisitedUrlAnalyser.analyse(logEntries)

        then:
        response.key == 'mostVisitedUrls'
        response.value != null

        and:
        def mostVisited1 = response.value.find(it -> it.endpoint == 'GET /analytics/some-path')
        mostVisited1.occurrences == 2

        and:
        def mostVisited2 = response.value.find(it -> it.endpoint == 'DELETE /some-endpoint/some-path/{id}')
        mostVisited2.occurrences == 2

        and:
        def mostVisited3 = response.value.find(it -> it.endpoint == 'GET /analytics/some-path')
        mostVisited3.occurrences == 2

    }
}
