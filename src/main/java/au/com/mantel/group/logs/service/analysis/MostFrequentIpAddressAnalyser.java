package au.com.mantel.group.logs.service.analysis;

import au.com.mantel.group.logs.service.analysis.model.LogEntry;
import au.com.mantel.group.logs.service.analysis.model.LogAnalysisResult;
import au.com.mantel.group.logs.service.analysis.model.MostFrequentIpAddressAnalysisResult;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MostFrequentIpAddressAnalyser implements LogAnalyser<List<MostFrequentIpAddressAnalysisResult>> {

    private static final int IP_COUNT = 3;

    private static final String KEY = "mostFrequentIpAddress";

    @Override
    public LogAnalysisResult<List<MostFrequentIpAddressAnalysisResult>> analyse(List<LogEntry> logEntries) {


        Map<String, Long> ipAddressOccurrences = logEntries.stream()
                .map(LogEntry::ipAddress)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        var topIpAddresses = ipAddressOccurrences.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(IP_COUNT)
                .map((entry) -> new MostFrequentIpAddressAnalysisResult(entry.getKey(), entry.getValue()))

                .collect(Collectors.toList());
        return new LogAnalysisResult<>(KEY, topIpAddresses);
    }
}
