package com.codeelevate.books.infra;

import com.codeelevate.books.domain.model.Book;
import com.codeelevate.books.domain.BookProvider;
import com.codeelevate.books.infra.database.entity.BookEntity;
import com.codeelevate.books.infra.database.repository.BookJPARepository;
import com.codeelevate.books.infra.mapper.BookDomainMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.domain.ExampleMatcher.matching;

/**
 * Implementation of the BookProvider interface.
 */
@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "book")
public class BookProviderImpl implements BookProvider {

    @Value("${spring.redis.ttlMinutes.recently}")
    private int TTL_RECENTLY;

    private static final String CACHE_PREFIX = "recently:";

    private final RedisTemplate<String, Book> bookRedisTemplate;

    private final BookJPARepository bookJPARepository;

    private final BookDomainMapper bookDomainMapper;


    @Override
    @Cacheable(value = "bookList")
    public Page<Book> getAllBooks(Pageable pageable, String author, String genre, Boolean exactMatching, String sessionId) {

        ExampleMatcher.StringMatcher stringMatcher;

        if (exactMatching) {
            stringMatcher = ExampleMatcher.StringMatcher.DEFAULT;
        } else {
            stringMatcher = ExampleMatcher.StringMatcher.CONTAINING;
        }

        BookEntity bookToSearch = new BookEntity();

        bookToSearch.setAuthor(author);
        bookToSearch.setMainGenre(genre);

        ExampleMatcher matcher = matching()
                .withStringMatcher(stringMatcher).withIgnoreCase();

        Page<BookEntity> booksPageable = bookJPARepository.findAll(Example.of(bookToSearch, matcher), pageable);

        return booksPageable.map(bookDomainMapper::toDomain);


    }

    @Override
    @Cacheable(value = "book")
    public Book getBookById(Long id, String sessionId) {

        BookEntity bookEntity = bookJPARepository.findById(id).orElse(null);

        return bookDomainMapper.toDomain(bookEntity);
    }



    /**
     * Adds a viewed book to the cache.
     *
     * @param book the book to add
     * @param sessionId the session ID
     */
    @Override
    public void addViewedBook(Book book, String sessionId) {
        String key = CACHE_PREFIX + sessionId;

        ZSetOperations<String, Book> zSetOps = bookRedisTemplate.opsForZSet();

        long timestamp = System.currentTimeMillis();

        zSetOps.add(key, book, timestamp);

        bookRedisTemplate.expire(key, TTL_RECENTLY, TimeUnit.MINUTES);
    }


    /**
     * Retrieves recently viewed books from the cache.
     *
     * @param limit the maximum number of books to retrieve
     * @param sessionId the session ID
     * @return a list of recently viewed books
     */
    @Override
    public List<Book> getRecentlyViewedBooks(int limit, String sessionId) {
        List<Book> recentBooks = new ArrayList<>();

        if (Strings.isEmpty(sessionId)) {
            return recentBooks;
        }

        String key = CACHE_PREFIX + sessionId;

        Set<ZSetOperations.TypedTuple<Book>> values = bookRedisTemplate.opsForZSet()
                .reverseRangeWithScores(key, 0, limit - 1);

        if (values != null && !values.isEmpty()) {
            values.forEach(bookTuple -> recentBooks.add(bookTuple.getValue()));
        }

        return recentBooks;
    }


}
