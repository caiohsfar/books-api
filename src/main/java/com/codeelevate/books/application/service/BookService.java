package com.codeelevate.books.application.service;

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

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookDTOMapper bookDTOMapper;

    private final BookUseCases bookUseCases;


    public Page<BookDTO> getAllBooks(Pageable pageable, String author, String genre, Boolean exactMatching, String sessionId) {
        Page<Book> books = bookUseCases.getAllBooks(pageable, author, genre, exactMatching, sessionId);

        return books.map(bookDTOMapper::toDTO);
    }

    public BookDTO getBookById(Long id, String sessionId) {

        return bookDTOMapper.toDTO(bookUseCases.getBookById(id, sessionId));
    }


    public List<BookDTO> getRecentlyViewed(int limit, String sessionId) {

        return bookUseCases.getRecentlyViewed(limit, sessionId).stream().map(bookDTOMapper::toDTO).collect(Collectors.toList());
    }

}