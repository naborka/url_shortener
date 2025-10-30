package natera.task.local.url_shortener.service.impl;

import java.time.Duration;
import java.time.Instant;

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
    public Page<TopUrlAnalytics> getTopUrls(int limit, Duration window) {
        Instant since = windowToSince(window);
        Pageable pageable = PageRequest.of(0, limit);
        Page<ShortUrl> urls = shortUrlRepository.findTopByLastAccessedDateAfterOrderByClickCountDesc(since, pageable);
        return urls.map(this::mapToTopUrlAnalytics);
    }

    private Instant windowToSince(Duration window) {
        if (window == null) {
            return Instant.MIN;
        }
        return Instant.now().minus(window);
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