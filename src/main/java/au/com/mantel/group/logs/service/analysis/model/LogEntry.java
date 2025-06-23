package au.com.mantel.group.logs.service.analysis.model;

public record LogEntry(
        String ipAddress,
        String httpMethod,
        String endpointUrl,
        int httpStatusCode
) {
    public static LogEntry invalid() {
        return new LogEntry("unknown", "-", "", 0);
    }

    public boolean isValid() {
        return ipAddress != null && !ipAddress.equals("unknown");
    }
}
