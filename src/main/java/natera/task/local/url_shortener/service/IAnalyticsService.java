package natera.task.local.url_shortener.service;

import java.time.Duration;

import org.springframework.data.domain.Page;

import natera.task.local.url_shortener.api.dto.TopUrlAnalytics;

public interface IAnalyticsService {
    Page<TopUrlAnalytics> getTopUrls(int limit, Duration window);
}