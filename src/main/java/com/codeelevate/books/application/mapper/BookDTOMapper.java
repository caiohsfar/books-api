package com.codeelevate.books.application.mapper;

import com.codeelevate.books.application.dto.BookDTO;
import com.codeelevate.books.domain.model.Book;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting Book to BookDTO.
 */
@Mapper(componentModel = "spring")
public interface BookDTOMapper {

    /**
     * Converts a Book to a BookDTO.
     *
     * @param book the book to convert
     * @return the converted BookDTO
     */
    BookDTO toDTO(Book book);
}