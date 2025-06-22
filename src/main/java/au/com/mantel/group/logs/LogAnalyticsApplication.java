package au.com.mantel.group.logs;

import au.com.mantel.group.logs.api.LogAnalyticsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogAnalyticsApplication {

    private static final Logger logger = LoggerFactory.getLogger(LogAnalyticsController.class);


    public static void main(String[] args) {
        logger.info("Starting LogAnalyticsApplication");
        SpringApplication.run(LogAnalyticsApplication.class, args);
        logger.info("LogAnalyticsApplication started successfully ...");
    }

}
