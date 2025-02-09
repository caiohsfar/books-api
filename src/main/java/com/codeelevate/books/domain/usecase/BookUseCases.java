package com.codeelevate.books.domain.usecase;

import com.codeelevate.books.domain.exception.BookNotFoundException;
import com.codeelevate.books.domain.model.Book;
import com.codeelevate.books.domain.provider.BookCachingProvider;
import com.codeelevate.books.domain.provider.BookRepositoryProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookUseCases {


    private final BookRepositoryProvider bookRepositoryProvider;
    private final BookCachingProvider bookCachingProvider;


    public Page<Book> getAllBooks(Pageable pageable, String author, String genre, Boolean exactMatching, String sessionId) {
        return bookRepositoryProvider.getAllBooks(pageable, author, genre, exactMatching, sessionId);
    }


    public Book getBookById(Long id, String sessionId) {

        Book book = bookRepositoryProvider.getBookById(id, sessionId);

        if (book == null) {
            throw new BookNotFoundException("Book not found with id " + id);
        }

        return book;
    }

    public List<Book> getRecentlyViewed(int limit, String sessionId) {
        return bookCachingProvider.getRecentlyViewedBooks(limit, sessionId);

    }

}