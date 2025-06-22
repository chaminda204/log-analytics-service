package au.com.mantel.group.logs.service;

import au.com.mantel.group.logs.service.analysis.LogAnalyser;
import au.com.mantel.group.logs.api.model.LogAnalyticsResponse;
import au.com.mantel.group.logs.api.model.LogEntry;
import au.com.mantel.group.logs.service.analysis.model.LogAnalysisResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LogAnalyticsService {

    private static final Logger logger = LoggerFactory.getLogger(LogAnalyticsService.class);
    public static final String UNKNOWN_LOG_LINE = "unknown";


    private LogParser logParser;

    private List<LogAnalyser> analysers;


    public LogAnalyticsService(List<LogAnalyser> analysers, LogParser logParser) {
        this.analysers = analysers;
        this.logParser = logParser;
    }

    public Mono<LogAnalyticsResponse> analyse(FilePart filePart) {
        return filePart.content()
                .flatMap(LogAnalyticsService::readLogLines)
                .map(logParser::parse)
                .filter(logline -> logline.ipAddress() != UNKNOWN_LOG_LINE)
                .collectList()
                .map(this::runAnalytics);
    }

    private static Flux<String> readLogLines(DataBuffer buffer) {
        try {
            String content = new String(buffer.asInputStream().readAllBytes(), StandardCharsets.UTF_8);
            return Flux.fromStream(content.lines());
        } catch (IOException ex) {
            logger.error("Error reading log lines {}", ex.getMessage());
            return Flux.error(new RuntimeException("Failed to read uploaded file", ex));
        }
    }

    private LogAnalyticsResponse runAnalytics(List<LogEntry> entries) {
        logger.info("running analytics for {} log entries", entries.size());
        Map<String, Object> results = analysers.stream()
                .map(analyser -> analyser.analyse(entries))
                .collect(Collectors.toMap(LogAnalysisResult::getKey, LogAnalysisResult::getValue));

        return new LogAnalyticsResponse(results);
    }
}