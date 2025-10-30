package natera.task.local.url_shortener.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import natera.task.local.url_shortener.api.dto.UrlMetricsResponse;
import natera.task.local.url_shortener.data.model.ShortUrl;
import natera.task.local.url_shortener.data.repository.ShortUrlRepository;
import natera.task.local.url_shortener.service.IMetricsService;

@Service
public class MetricsService implements IMetricsService {

    private final ShortUrlRepository shortUrlRepository;

    public MetricsService(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<UrlMetricsResponse> getMetrics(Pageable pageable) {
        Page<ShortUrl> urlPage = shortUrlRepository.findAll(pageable);
        List<UrlMetricsResponse> responses = urlPage.getContent().stream()
                .map(url -> new UrlMetricsResponse(
                        url.getId(),
                        url.getShortCode(),
                        url.getOriginalUrl(),
                        url.getClickCount(),
                        url.getLastAccessedDate()))
                .toList();
        return new PageImpl<>(responses, pageable, urlPage.getTotalElements());
    }
}
