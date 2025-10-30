package natera.task.local.url_shortener.service;

import natera.task.local.url_shortener.api.dto.CreateShortUrlRequest;
import natera.task.local.url_shortener.api.dto.CreateShortUrlResponse;

public interface IUrlShortenerService {
    CreateShortUrlResponse createShortUrl(CreateShortUrlRequest request);
    String getOriginalUrl(String shortCode);
}