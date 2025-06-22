package au.com.mantel.group.logs.api.model;

public record LogEntry(
        String ipAddress,
        String httpMethod,
        String endpointUrl,
        int httpStatusCode
) {
    public static LogEntry invalid(String message) {
        return new LogEntry(message, "-", "", 0);
    }
}
