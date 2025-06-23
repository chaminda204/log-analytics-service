package au.com.mantel.group.logs.model;

import java.util.Map;

public record LogAnalyticsResponse(
        Map<String, Object> results) {
}
