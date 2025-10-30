package natera.task.local.url_shortener.api.controller;

import java.time.Duration;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import natera.task.local.url_shortener.api.dto.TopUrlAnalytics;
import natera.task.local.url_shortener.service.IAnalyticsService;

@RestController
@RequestMapping("/v1/analytics")
public class AnalyticsController {

    private final IAnalyticsService analyticsService;

    public AnalyticsController(IAnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/top")
    public ResponseEntity<Page<TopUrlAnalytics>> getTopUrls(
            @RequestParam(required = false) Duration window,
            @RequestParam(defaultValue = "10") int limit) {
        Page<TopUrlAnalytics> topUrls = analyticsService.getTopUrls(limit, window);
        return ResponseEntity.ok(topUrls);
    }
}