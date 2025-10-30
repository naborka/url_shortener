package natera.task.local.url_shortener.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import natera.task.local.url_shortener.api.dto.CreateShortUrlRequest;
import natera.task.local.url_shortener.api.dto.CreateShortUrlResponse;
import natera.task.local.url_shortener.service.IUrlShortenerService;

@RestController
@RequestMapping("/api/urls")
public class UrlShortenerController {

    private final IUrlShortenerService urlShortenerService;

    public UrlShortenerController(IUrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping
    public ResponseEntity<CreateShortUrlResponse> createShortUrl(@RequestBody CreateShortUrlRequest createRequest) {
        CreateShortUrlResponse response = urlShortenerService.createShortUrl(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}