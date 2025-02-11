package com.codeelevate.books.domain.provider;

import com.codeelevate.books.domain.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BookProvider {

    Page<Book> getAllBooks(Pageable pageable, String author, String genre, Boolean exactMatching, String sessionId);

    Book getBookById(Long id, String sessionId);

    void addViewedBook(Book book, String sessionId);

    List<Book> getRecentlyViewedBooks(int limit, String sessionId);

}
