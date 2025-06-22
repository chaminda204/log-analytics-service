package au.com.mantel.group.logs.service.analysis.model;

public class LogAnalysisResult<R> {

    private String key;
    private R value;

    public LogAnalysisResult(String key, R result) {
        this.key = key;
        this.value = result;
    }
    public String getKey() {
        return key;
    }

    public R getValue() {
        return value;
    }
}
