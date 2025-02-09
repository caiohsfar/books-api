package com.codeelevate.books.domain.provider;

import com.codeelevate.books.domain.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BookRepositoryProvider {

    Page<Book> getAllBooks(Pageable pageable, String author, String genre, Boolean exactMatching, String sessionId);

    Book getBookById(Long id);

}
