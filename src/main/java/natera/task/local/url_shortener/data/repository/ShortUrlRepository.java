package natera.task.local.url_shortener.data.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import natera.task.local.url_shortener.data.model.ShortUrl;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    Optional<ShortUrl> findByShortCode(String shortCode);
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE ShortUrl s SET s.clickCount = s.clickCount + 1, s.lastAccessedDate = :now WHERE s.id = :id")
    int incrementClickCountAndSetLastAccessedDate(@Param("id") Long id, @Param("now") Instant now);

    @Query(value = "SELECT * FROM short_url WHERE last_accessed_date > :since ORDER BY click_count DESC", nativeQuery = true)
    Page<ShortUrl> findTop(Instant since, Pageable pageable);
}