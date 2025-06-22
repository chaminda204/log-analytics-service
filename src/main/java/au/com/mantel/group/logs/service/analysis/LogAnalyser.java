package au.com.mantel.group.logs.service.analysis;

import au.com.mantel.group.logs.api.model.LogEntry;
import au.com.mantel.group.logs.service.analysis.model.LogAnalysisResult;

import java.util.List;

public interface LogAnalyser<R> {

    LogAnalysisResult<R> analyse(List<LogEntry> logEntries);
}
