package natera.task.local.url_shortener.service;

import org.springframework.data.domain.Page;

import natera.task.local.url_shortener.api.dto.TopUrlAnalytics;

public interface IAnalyticsService {
    Page<TopUrlAnalytics> getTopUrls(int limit, Integer days);
}