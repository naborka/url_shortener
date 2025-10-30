package natera.task.local.url_shortener.api.dto;

import java.time.Instant;

public record TopUrlAnalytics(
    String shortCode,
    String originalUrl,
    Long clicksWithinWindow,
    Instant lastAccessedDate
) {
}