package com.codeelevate.books.application.service.impl;

import com.codeelevate.books.application.service.BookService;
import com.codeelevate.books.application.dto.BookDTO;
import com.codeelevate.books.application.mapper.BookDTOMapper;
import com.codeelevate.books.domain.model.Book;
import com.codeelevate.books.domain.usecase.BookUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the BookService interface.
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDTOMapper bookDTOMapper;
    private final BookUseCases bookUseCases;

    /**
     * Retrieves all books with optional filtering.
     *
     * @param pageable the pagination information
     * @param author the author to filter by
     * @param genre the genre to filter by
     * @param exactMatching whether to use exact matching for filtering
     * @param sessionId the session ID for caching
     * @return a pageable list of book DTOs
     */
    public Page<BookDTO> getAllBooks(Pageable pageable, String author, String genre, Boolean exactMatching, String sessionId) {
        Page<Book> books = bookUseCases.getAllBooks(pageable, author, genre, exactMatching, sessionId);
        return books.map(bookDTOMapper::toDTO);
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id the book ID
     * @param sessionId the session ID for caching
     * @return the book DTO
     */
    public BookDTO getBookById(Long id, String sessionId) {
        return bookDTOMapper.toDTO(bookUseCases.getBookById(id, sessionId));
    }

    /**
     * Retrieves recently viewed books.
     *
     * @param limit the maximum number of books to retrieve
     * @param sessionId the session ID for caching
     * @return a list of recently viewed book DTOs
     */
    public List<BookDTO> getRecentlyViewed(int limit, String sessionId) {
        return bookUseCases.getRecentlyViewed(limit, sessionId).stream().map(bookDTOMapper::toDTO).collect(Collectors.toList());
    }

}