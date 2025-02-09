package com.codeelevate.books.infra;

import com.codeelevate.books.domain.model.Book;
import com.codeelevate.books.domain.provider.BookCachingProvider;
import com.codeelevate.books.domain.provider.BookRepositoryProvider;
import com.codeelevate.books.infra.database.entity.BookEntity;
import com.codeelevate.books.infra.database.repository.BookJPARepository;
import com.codeelevate.books.infra.mapper.BookDomainMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static org.springframework.data.domain.ExampleMatcher.matching;

@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "book")
public class BookRepositoryProviderImpl implements BookRepositoryProvider {

    private final BookJPARepository bookJPARepository;
    private final BookDomainMapper bookDomainMapper;

    private final BookCachingProvider bookCachingProvider;

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

        if (Strings.isNotEmpty(sessionId)) {
            booksPageable.getContent().forEach(book -> bookCachingProvider.addViewedBook(book, sessionId));
        }

        return booksPageable.map(bookDomainMapper::toDomain);


    }

    @Override
    @Cacheable(value = "book")
    public Book getBookById(Long id, String sessionId) {

        BookEntity bookEntity = bookJPARepository.findById(id).orElse(null);

        if (Strings.isNotEmpty(sessionId)) {
            bookCachingProvider.addViewedBook(bookEntity, sessionId);
        }


        return bookDomainMapper.toDomain(bookEntity);
    }


}
