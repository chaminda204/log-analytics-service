package au.com.mantel.group.logs

import au.com.mantel.group.logs.api.model.LogAnalyticsResponse
import au.com.mantel.group.logs.api.model.LogEntry

trait LogAnalyticsFixtures {

    def logAnalyticsResponse(Map options = [:]) {

        def data = [
                mostVisitedUrls          : 'GET somepath/',
                mostActiveIpAddresses    : ['192.168.1.1', '192.176.1.12', '192.176.1.18'],
                numberOfUniqueIpAddresses: 3

        ] << options
        return new LogAnalyticsResponse(data)
    }

    def logEntry(Map options = [:]) {
        def data = [
                ipAddress     : '192.168.1.1',
                httpMethod    : 'GET',
                endpointUrl   : '/some-endpoint/some-path',
                httpStatusCode: 200

        ] << options
        return new LogEntry(data.ipAddress, data.httpMethod, data.endpointUrl, data.httpStatusCode)
    }

    def logEntries() {
        return List<LogEntry>.of(
                logEntry(ipAddress: '192.168.1.1', httpMethod: 'GET', endpointUrl: '/analytics/some-path', httpStatusCode: 200),
                logEntry(ipAddress: '192.168.1.7', httpMethod: 'POST', endpointUrl: '/some-endpoint/some-path', httpStatusCode: 200),
                logEntry(ipAddress: '192.168.1.1', httpMethod: 'PATCH', endpointUrl: '/customers/{id}', httpStatusCode: 200),
                logEntry(ipAddress: '192.168.1.8', httpMethod: 'POST', endpointUrl: '/some-endpoint/some-path', httpStatusCode: 200),
                logEntry(ipAddress: '192.168.1.1', httpMethod: 'DELETE', endpointUrl: '/some-endpoint/some-path/{id}', httpStatusCode: 200),
                logEntry(ipAddress: '192.168.1.7', httpMethod: 'GET', endpointUrl: '/customers/{id}', httpStatusCode: 200),
                logEntry(ipAddress: '192.168.1.1', httpMethod: 'GET', endpointUrl: '/customers/{id}', httpStatusCode: 200),
                logEntry(ipAddress: '192.168.1.5', httpMethod: 'DELETE', endpointUrl: '/some-endpoint/some-path/{id}', httpStatusCode: 200),
                logEntry(ipAddress: '192.168.1.5', httpMethod: 'PUT', endpointUrl: '/some-endpoint/some-path', httpStatusCode: 200),
                logEntry(ipAddress: '192.168.1.3', httpMethod: 'GET', endpointUrl: '/analytics/some-path', httpStatusCode: 200),
                logEntry(ipAddress: '192.168.1.7', httpMethod: 'GET', endpointUrl: '/some-endpoint/some-path', httpStatusCode: 200)
        )
    }


}