package au.com.mantel.group.logs.api;

import au.com.mantel.group.logs.api.model.LogAnalyticsResponse;
import au.com.mantel.group.logs.service.LogAnalyticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/api/analytics/logs")
public class LogAnalyticsController {

    private LogAnalyticsService logAnalyticsService;

    private static final Logger logger = LoggerFactory.getLogger(LogAnalyticsController.class);


    public LogAnalyticsController(LogAnalyticsService logAnalyticsService) {
        this.logAnalyticsService = logAnalyticsService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<LogAnalyticsResponse> analyse(@RequestPart("file") FilePart filePart) {
        logger.info("received log file for analysis {}", filePart.filename());
        return logAnalyticsService.analyse(filePart);
    }

}
