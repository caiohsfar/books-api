package com.codeelevate.books.application;

import com.codeelevate.books.application.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BookService {

    Page<BookDTO> getAllBooks(Pageable pageable, String author, String genre, Boolean exactMatching, String sessionId);

    BookDTO getBookById(Long id, String sessionId);

    List<BookDTO> getRecentlyViewed(int limit, String sessionId);
}
