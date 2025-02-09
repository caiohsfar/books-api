package com.codeelevate.books.infra;

import com.codeelevate.books.domain.model.Book;
import com.codeelevate.books.domain.provider.BookCachingProvider;
import com.codeelevate.books.infra.database.entity.BookEntity;
import com.codeelevate.books.infra.mapper.BookDomainMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class BookCachingProviderImpl implements BookCachingProvider {

    private static final String CACHE_PREFIX = "recently:";

    private final RedisTemplate<String, BookEntity> redisTemplate;

    private final BookDomainMapper mapper;

    @Override
    public void addViewedBook(BookEntity book, String sessionId) {
        String key = CACHE_PREFIX + sessionId;

        ZSetOperations<String, BookEntity> zSetOps = redisTemplate.opsForZSet();

        long timestamp = System.currentTimeMillis();

        zSetOps.add(key, book, timestamp);
    }

    @Override
    public List<Book> getRecentlyViewedBooks(int limit, String sessionId) {
        List<Book> recentBooks = new ArrayList<>();

        if (Strings.isEmpty(sessionId)) {
            return recentBooks;
        }

        String key = CACHE_PREFIX + sessionId;

        Set<ZSetOperations.TypedTuple<BookEntity>> values = redisTemplate.opsForZSet()
                .reverseRangeWithScores(key, 0, limit - 1);

        if (values != null && !values.isEmpty()) {
            values.forEach(bookTuple -> recentBooks.add(mapper.toDomain(bookTuple.getValue())));
        }

        return recentBooks;
    }
}
