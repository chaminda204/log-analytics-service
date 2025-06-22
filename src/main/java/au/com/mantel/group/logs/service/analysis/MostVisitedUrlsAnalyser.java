package au.com.mantel.group.logs.service.analysis;

import au.com.mantel.group.logs.api.model.LogEntry;
import au.com.mantel.group.logs.service.analysis.model.LogAnalysisResult;
import au.com.mantel.group.logs.service.analysis.model.MostVisitedUrlAnalysisResult;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MostVisitedUrlsAnalyser implements LogAnalyser<List<String>> {

    private static final int ENDPOINT_LIMIT = 3;

    @Override
    public LogAnalysisResult<List<String>> analyse(List<LogEntry> logEntries) {

        Map<String, Long> endpointOccurrences = logEntries.stream()
                .map(entry -> entry.httpMethod() + " " + entry.endpointUrl())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));


        var topThreeEndpoints = endpointOccurrences.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(ENDPOINT_LIMIT)
                .map((entry) -> new MostVisitedUrlAnalysisResult(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return new LogAnalysisResult("mostVisitedUrls", topThreeEndpoints);
    }
}
