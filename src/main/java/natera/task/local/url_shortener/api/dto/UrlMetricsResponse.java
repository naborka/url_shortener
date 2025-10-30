package natera.task.local.url_shortener.api.dto;

import java.time.Instant;

public class UrlMetricsResponse {
    private Long id;
    private String shortCode;
    private String originalUrl;
    private Long clickCount;
    private Instant lastAccessedDate;

    public UrlMetricsResponse() {
    }

    public UrlMetricsResponse(Long id, String shortCode, String originalUrl, Long clickCount, Instant lastAccessedDate) {
        this.id = id;
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
        this.clickCount = clickCount;
        this.lastAccessedDate = lastAccessedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public Instant getLastAccessedDate() {
        return lastAccessedDate;
    }

    public void setLastAccessedDate(Instant lastAccessedDate) {
        this.lastAccessedDate = lastAccessedDate;
    }
}