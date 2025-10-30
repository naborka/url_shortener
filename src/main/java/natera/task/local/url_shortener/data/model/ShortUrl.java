package natera.task.local.url_shortener.data.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = {"short_code"}),
    indexes = {
        @Index(name = "idx_shorturl_shortcode", columnList = "short_code")
    }
)
public class ShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 8)
    private String shortCode;
    @Column(length = 1000)
    private String originalUrl;
    @Column
    private Instant creationDate;
    @Column
    private Instant lastAccessedDate;
    @Column
    private Long clickCount = 0L;

    public ShortUrl() {
    }

    public ShortUrl(Long id, String shortCode, String originalUrl, Instant creationDate, Instant lastAccessedDate, Long clickCount) {
        this.id = id;
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
        this.creationDate = creationDate;
        this.lastAccessedDate = lastAccessedDate;
        this.clickCount = clickCount == null ? 0L : clickCount;
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

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getLastAccessedDate() {
        return lastAccessedDate;
    }

    public void setLastAccessedDate(Instant lastAccessedDate) {
        this.lastAccessedDate = lastAccessedDate;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }
}