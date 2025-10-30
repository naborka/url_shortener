package natera.task.local.url_shortener.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import natera.task.local.url_shortener.api.dto.CreateShortUrlRequest;
import natera.task.local.url_shortener.api.dto.CreateShortUrlResponse;
import natera.task.local.url_shortener.api.dto.UrlMetricsResponse;
import natera.task.local.url_shortener.service.IMetricsService;
import natera.task.local.url_shortener.service.IUrlShortenerService;

@RestController
@RequestMapping("/api/urls")
public class UrlShortenerController {

    private final IUrlShortenerService urlShortenerService;
    private final IMetricsService metricsService;

    public UrlShortenerController(IUrlShortenerService urlShortenerService, IMetricsService metricsService) {
        this.urlShortenerService = urlShortenerService;
        this.metricsService = metricsService;
    }

    @PostMapping
    public ResponseEntity<CreateShortUrlResponse> createShortUrl(@RequestBody CreateShortUrlRequest createRequest) {
        CreateShortUrlResponse response = urlShortenerService.createShortUrl(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode) {
        String originalUrl = urlShortenerService.getOriginalUrl(shortCode);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, originalUrl)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        urlShortenerService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<UrlMetricsResponse>> getAllUrlsWithMetrics(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UrlMetricsResponse> responses = metricsService.getMetrics(pageable);
        return ResponseEntity.ok(responses);
    }
}
