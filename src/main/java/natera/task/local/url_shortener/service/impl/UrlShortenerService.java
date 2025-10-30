package natera.task.local.url_shortener.service.impl;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import natera.task.local.url_shortener.api.dto.CreateShortUrlRequest;
import natera.task.local.url_shortener.api.dto.CreateShortUrlResponse;
import natera.task.local.url_shortener.data.model.ShortUrl;
import natera.task.local.url_shortener.data.repository.ShortUrlRepository;
import natera.task.local.url_shortener.service.IUrlShortenerService;
import natera.task.local.url_shortener.util.ShortCodeGenerator;

@Service
public class UrlShortenerService implements IUrlShortenerService {

    private final ShortUrlRepository shortUrlRepository;

    public UrlShortenerService(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    @Override
    @Transactional
    public CreateShortUrlResponse createShortUrl(CreateShortUrlRequest request) {
        String originalUrl = request.getOriginalUrl();
        String shortCode = generateUniqueShortCode();

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setShortCode(shortCode);
        shortUrl.setOriginalUrl(originalUrl);
        shortUrl.setCreationDate(Instant.now());
        shortUrl.setLastAccessedDate(null);
        shortUrl.setClickCount(0L);

        ShortUrl savedUrl = shortUrlRepository.save(shortUrl);

        return new CreateShortUrlResponse(
                savedUrl.getShortCode(),
                savedUrl.getOriginalUrl());
    }

    @Override
    @Transactional
    public String getOriginalUrl(String shortCode) {
        Optional<ShortUrl> optionalShortUrl = shortUrlRepository.findByShortCode(shortCode);
        if (optionalShortUrl.isEmpty()) {
            throw new IllegalArgumentException("short code not found: " + shortCode);
        }
        ShortUrl shortUrl = optionalShortUrl.get();
        shortUrlRepository.incrementClickCountAndSetLastAccessedDate(shortUrl.getId(), Instant.now());
        return shortUrl.getOriginalUrl();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (shortUrlRepository.existsById(id)) {
            shortUrlRepository.deleteById(id);
        }
    }

    private String generateUniqueShortCode() {
        for (int i = 0; i < 10; i++) {
            String shortCode = ShortCodeGenerator.generateShortCode();
            Optional<ShortUrl> existing = shortUrlRepository.findByShortCode(shortCode);
            if (existing.isEmpty()) {
                return shortCode;
            }
        }
        throw new IllegalStateException("cant generate uniq short code");
    }
}
