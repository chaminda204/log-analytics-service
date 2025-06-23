import au.com.mantel.group.logs.service.LogAnalyticsService
import au.com.mantel.group.logs.service.LogParser
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import spock.lang.Specification;
import spock.lang.Subject;

import au.com.mantel.group.logs.service.analysis.model.LogEntry;
import au.com.mantel.group.logs.model.LogAnalyticsResponse;
import au.com.mantel.group.logs.service.analysis.LogAnalyser;
import au.com.mantel.group.logs.service.analysis.model.LogAnalysisResult;

class LogAnalyticsServiceSpec extends Specification {

    def logParser = Mock(LogParser)
    def analyser1 = Mock(LogAnalyser)
    def analyser2 = Mock(LogAnalyser)

    @Subject
    def service = new LogAnalyticsService([analyser1, analyser2], logParser)

    def "should parse file content and run all analysers"() {
        given:
        def filePart = Mock(FilePart)

        and:
        def fileContent = '127.0.0.1 - - [10/Jul/2018:22:21:28 +0200] "GET /home HTTP/1.1" 200 1234'
        DataBuffer buffer = new DefaultDataBufferFactory().wrap(fileContent.bytes)

        and:
        def parsedEntry = new LogEntry("127.0.0.1", "GET", "/home", 200)
        
        when:
        def result = service.analyse(filePart).block()

        then:
        result instanceof LogAnalyticsResponse
        result.results.size() == 2
        result.results["someResult"] == 1
        result.results["someOtherResult"] == ["/home"]

        filePart.content() >> Flux.just(buffer)
        1 * logParser.parse(_) >> parsedEntry
        1 * analyser1.analyse(_) >> new LogAnalysisResult("someResult", 1)
        1 * analyser2.analyse(_) >> new LogAnalysisResult("someOtherResult", ["/home"])
    }
}