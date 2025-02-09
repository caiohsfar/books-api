package com.codeelevate.books.domain.provider;


import com.codeelevate.books.domain.model.Book;
import com.codeelevate.books.infra.database.entity.BookEntity;

import java.util.List;

public interface BookCachingProvider {

    void addViewedBook(BookEntity book, String sessionId);

    List<Book> getRecentlyViewedBooks(int limit, String sessionId);

}
