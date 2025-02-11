package com.codeelevate.books.domain.usecase;

import com.codeelevate.books.domain.exception.BookNotFoundException;
import com.codeelevate.books.domain.model.Book;
import com.codeelevate.books.domain.BookProvider;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Use cases for managing books.
 */
@Component
@RequiredArgsConstructor
public class BookUseCases {

    private final BookProvider bookProvider;

    /**
     * Retrieves all books with optional filtering.
     *
     * @param pageable the pagination information
     * @param author the author to filter by
     * @param genre the genre to filter by
     * @param exactMatching whether to use exact matching for filtering
     * @param sessionId the session ID for caching
     * @return a pageable list of books
     */
    public Page<Book> getAllBooks(Pageable pageable, String author, String genre, Boolean exactMatching, String sessionId) {
        Page<Book> booksPageable = bookProvider.getAllBooks(pageable, author, genre, exactMatching, sessionId);

        if (Strings.isNotEmpty(sessionId)) {
            booksPageable.getContent().forEach(book -> bookProvider.addViewedBook(book, sessionId));
        }

        return booksPageable;
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id the book ID
     * @param sessionId the session ID for caching
     * @return the book
     */
    public Book getBookById(Long id, String sessionId) {
        Book book = bookProvider.getBookById(id, sessionId);

        if (book == null) {
            throw new BookNotFoundException("Book not found with id " + id);
        }

        if (Strings.isNotEmpty(sessionId)) {
            bookProvider.addViewedBook(book, sessionId);
        }
        return book;
    }

    /**
     * Retrieves recently viewed books.
     *
     * @param limit the maximum number of books to retrieve
     * @param sessionId the session ID for caching
     * @return a list of recently viewed books
     */
    public List<Book> getRecentlyViewed(int limit, String sessionId) {
        return bookProvider.getRecentlyViewedBooks(limit, sessionId);
    }

}