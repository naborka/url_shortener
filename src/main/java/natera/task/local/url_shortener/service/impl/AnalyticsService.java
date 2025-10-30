package natera.task.local.url_shortener.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import natera.task.local.url_shortener.api.dto.TopUrlAnalytics;
import natera.task.local.url_shortener.data.model.ShortUrl;
import natera.task.local.url_shortener.data.repository.ShortUrlRepository;
import natera.task.local.url_shortener.service.IAnalyticsService;

@Service
public class AnalyticsService implements IAnalyticsService {

    private final ShortUrlRepository shortUrlRepository;

    public AnalyticsService(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    @Override
    public Page<TopUrlAnalytics> getTopUrls(int limit, Integer days) {
        Instant since = windowToSince(days);
        Pageable pageable = PageRequest.of(0, limit);
        Page<ShortUrl> urls = shortUrlRepository.findTop(since, pageable);
        return urls.map(this::mapToTopUrlAnalytics);
    }

    private Instant windowToSince(Integer days) {
        if (days == null) {
            return Instant.EPOCH;
        }
        return Instant.now().minus(days * 86400L, ChronoUnit.SECONDS);
    }

    private TopUrlAnalytics mapToTopUrlAnalytics(ShortUrl shortUrl) {
        return new TopUrlAnalytics(
            shortUrl.getShortCode(),
            shortUrl.getOriginalUrl(),
            shortUrl.getClickCount(),
            shortUrl.getLastAccessedDate()
        );
    }
}