package au.com.mantel.group.logs.service.analysis

import au.com.mantel.group.logs.LogAnalyticsFixtures
import spock.lang.Specification

class UniqueIpAddressCountAnalyserSpec extends Specification implements LogAnalyticsFixtures {

    def uniqueIpAddressAnalyser = new UniqueIpAddressCountAnalyser()

    def 'should analyse log entries and return no of unique ip addresses'() {
        given:
        def logEntries = logEntries()

        when:
        var response = uniqueIpAddressAnalyser.analyse(logEntries)

        then:
        response.key == 'noOfUniqueIpAddresses'
        response.value == 5
    }
}
