package com.codeelevate.books.application.mapper;

import com.codeelevate.books.application.dto.BookDTO;
import com.codeelevate.books.domain.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookDTOMapper {

    BookDTO toDTO(Book book);
}