package com.codeelevate.books.application.resource.impl;

import com.codeelevate.books.application.dto.BookDTO;
import com.codeelevate.books.application.service.BookService;
import com.codeelevate.books.application.resource.BookResource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing books.
 */
@RestController
@RequiredArgsConstructor
public class BookResourceImpl implements BookResource {

    private final BookService bookService;

    /**
     * Retrieves all books with optional filtering and pagination.
     *
     * @param pageable     the pagination information
     * @param exactMatching whether to use exact matching for filtering
     * @param author        the author to filter by
     * @param mainGenre     the main genre to filter by
     * @param sessionId     the session ID for caching
     * @return a pageable list of book DTOs
     */
    @Override
    public ResponseEntity<Page<BookDTO>> getAllBooks(Pageable pageable, boolean exactMatching, String author, String mainGenre, String sessionId) {
        return ResponseEntity.ok(bookService.getAllBooks(pageable, author, mainGenre, exactMatching, sessionId));
    }


    /**
     * Retrieves a book by its ID.
     *
     * @param id        the book ID
     * @param sessionId the session ID for caching
     * @return the book DTO
     */
    @Override
    public ResponseEntity<BookDTO> getBookById(Long id, String sessionId) {
        return ResponseEntity.ok(bookService.getBookById(id, sessionId));
    }


    /**
     * Retrieves recently viewed books.
     *
     * @param limit     the maximum number of books to retrieve
     * @param sessionId the session ID for caching
     * @return a list of recently viewed book DTOs
     */
    @Override
    public ResponseEntity<List<BookDTO>> getRecentlyViewed(int limit, String sessionId) {
        return ResponseEntity.ok(bookService.getRecentlyViewed(limit, sessionId));
    }
}
