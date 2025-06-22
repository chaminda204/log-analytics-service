package au.com.mantel.group.logs.api.model;

import java.util.Map;

public record LogAnalyticsResponse(
        Map<String, Object> results) {
}
