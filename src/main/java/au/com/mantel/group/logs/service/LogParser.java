package au.com.mantel.group.logs.service;

import au.com.mantel.group.logs.api.model.LogEntry;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LogParser {
    private static final String IP_PATTERN = "(?<ip>\\S+)";
    private static final String USER_PATTERN = "(?<data>\\S+)";
    private static final String TIMESTAMP_PATTERN = "\\[(?<timestamp>[^\\]]+)\\]";
    private static final String REQUEST_PATTERN = "\"(?<method>\\S+) (?<url>\\S+) (?<protocol>[^\"]+)\"";
    private static final String STATUS_PATTERN = "(?<status>\\d{3})";
    private static final String SIZE_PATTERN = "(?<size>\\d+)";
    private static final String REFERRER_PATTERN = "\"(?<referrer>[^\"]*)\"";
    private static final String USER_AGENT_PATTERN = "\"(?<userAgent>[^\"]*)\"";
    private static final String EXTRA = String.join(" ", REFERRER_PATTERN, USER_AGENT_PATTERN);

    private static final Pattern LOG_PATTERN = Pattern.compile(
            String.join(" ",
                    IP_PATTERN,
                    "-",                 // fixed dash
                    USER_PATTERN,
                    TIMESTAMP_PATTERN,
                    REQUEST_PATTERN,
                    STATUS_PATTERN,
                    SIZE_PATTERN,
                    EXTRA
            )
    );


    public LogEntry parse(String line) {
        Matcher matcher = LOG_PATTERN.matcher(line);

        if (!matcher.matches()) {
            return LogEntry.invalid("unknown");
        }

        var ip = matcher.group("ip");
        var method = matcher.group("method");
        var url = matcher.group("url");
        int status = parseInt(matcher.group("status"));

        return new LogEntry(ip, method, url, status);
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
