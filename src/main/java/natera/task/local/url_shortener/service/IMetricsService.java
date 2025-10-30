package natera.task.local.url_shortener.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import natera.task.local.url_shortener.api.dto.UrlMetricsResponse;

public interface IMetricsService {
    Page<UrlMetricsResponse> getMetrics(Pageable pageable);
}
