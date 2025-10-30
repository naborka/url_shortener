package natera.task.local.url_shortener.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import natera.task.local.url_shortener.api.dto.CreateShortUrlRequest;
import natera.task.local.url_shortener.api.dto.CreateShortUrlResponse;
import natera.task.local.url_shortener.data.model.ShortUrl;
import natera.task.local.url_shortener.data.repository.ShortUrlRepository;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceTest {

    @Mock
    private ShortUrlRepository shortUrlRepository;

    @InjectMocks
    private UrlShortenerService urlShortenerService;

    @Test
    void createShouldReturnResponse_validRequest() {
        String originalUrl = "https://google.com";
        CreateShortUrlRequest request = new CreateShortUrlRequest(originalUrl);
        String shortCode = "abc";
        ShortUrl savedUrl = new ShortUrl(null, shortCode, originalUrl, null, null, 0L);

        when(shortUrlRepository.findByShortCode(any(String.class))).thenReturn(Optional.empty());
        when(shortUrlRepository.save(any(ShortUrl.class))).thenReturn(savedUrl);

        CreateShortUrlResponse response = urlShortenerService.createShortUrl(request);

        verify(shortUrlRepository).save(any(ShortUrl.class));
        assertThat(response.getShortCode()).isEqualTo(shortCode);
        assertThat(response.getOriginalUrl()).isEqualTo(originalUrl);
    }

    @Test
    void getOriginalUrlShouldReturnUrlAndUpdateMetrics_validRequest() {
        String shortCode = "abc";
        String originalUrl = "https://google.com";
        Long id = 1L;
        ShortUrl shortUrl = new ShortUrl(id, shortCode, originalUrl, null, null, 0L);

        when(shortUrlRepository.findByShortCode(shortCode)).thenReturn(Optional.of(shortUrl));

        String result = urlShortenerService.getOriginalUrl(shortCode);

        verify(shortUrlRepository).incrementClickCountAndSetLastAccessedDate(eq(id), any(Instant.class));
        assertThat(result).isEqualTo(originalUrl);
    }

    @Test
    void getOriginalUrlShouldThrowException_invalidRequest() {
        String shortCode = "notexist";

        when(shortUrlRepository.findByShortCode(shortCode)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> urlShortenerService.getOriginalUrl(shortCode))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("short code not found: " + shortCode);
        verify(shortUrlRepository, never()).incrementClickCountAndSetLastAccessedDate(any(Long.class), any(Instant.class));
    }
}