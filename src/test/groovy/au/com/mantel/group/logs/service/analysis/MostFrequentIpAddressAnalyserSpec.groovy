package au.com.mantel.group.logs.service.analysis

import au.com.mantel.group.logs.LogAnalyticsFixtures
import spock.lang.Specification

class MostFrequentIpAddressAnalyserSpec extends Specification implements LogAnalyticsFixtures {

    def logAnalyser = new MostFrequentIpAddressAnalyser()

    def "should analyse results and return top 3 most active ip addresses"() {
        given:
        def logEntries = logEntries()

        when:
        var response = logAnalyser.analyse(logEntries)

        then:
        response != null
        response.getKey() == 'mostFrequentIpAddress'

        and: 'most frequent ip address should be 192.168.1.1 with 4 occurrences'
        def mostFrequent = response.value.find(it -> it.occurrences == 4)
        mostFrequent.ipAddress() == '192.168.1.1'

        and: 'second ip address should be 192.168.1.7 with 3 occurrences'
        def secondIp = response.value.find(it -> it.occurrences == 3)
        secondIp.ipAddress() == '192.168.1.7'

        and: 'third ip address should be 192.168.1.5 with 2 occurrences'
        def third = response.value.find(it -> it.occurrences == 2)
        third.ipAddress() == '192.168.1.5'

    }
}
