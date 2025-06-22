package au.com.mantel.group.logs.service

import au.com.mantel.group.logs.LogAnalyticsFixtures
import spock.lang.Specification

class LogParserSpec extends Specification implements LogAnalyticsFixtures {

    def logParser = new LogParser()

    def "should parse a valid log line"() {
        given:
        def logline = "177.71.128.21 - - [10/Jul/2018:22:21:28 +0200] \"GET /intranet-analytics/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\""

        when:
        def logEntry = logParser.parse(logline)

        then:
        logEntry != null
        logEntry.ipAddress() == '177.71.128.21'
        logEntry.endpointUrl() == '/intranet-analytics/'
        logEntry.httpMethod() == 'GET'
        logEntry.httpStatusCode() == 200
    }

    def "should return unknown for invalid log entry"(){
        given:
        def logline = "some invalid content - - 33sdfdfsdf vfdsf1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\""

        when:
        def logEntry = logParser.parse(logline)

        then:
        logEntry.ipAddress() == 'unknown'
    }

}
