package au.com.mantel.group.logs.service.analysis;

import au.com.mantel.group.logs.service.analysis.model.LogEntry;
import au.com.mantel.group.logs.service.analysis.model.LogAnalysisResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UniqueIpAddressCountAnalyser implements LogAnalyser<Long> {

    private static final String KEY = "noOfUniqueIpAddresses";

    @Override
    public LogAnalysisResult<Long> analyse(List<LogEntry> logEntries) {
        var uniqueIps = logEntries.stream()
                .map(LogEntry::ipAddress)
                .distinct()
                .count();

        return new LogAnalysisResult(KEY, uniqueIps);
    }
}
