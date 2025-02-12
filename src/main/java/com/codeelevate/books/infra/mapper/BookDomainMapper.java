package com.codeelevate.books.infra.mapper;

import com.codeelevate.books.domain.model.Book;
import com.codeelevate.books.infra.database.entity.BookEntity;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting BookEntity to Book domain model.
 */
@Mapper(componentModel = "spring")
public interface BookDomainMapper {

    /**
     * Converts a BookEntity to a Book domain model.
     *
     * @param bookEntity the book entity to convert
     * @return the converted Book domain model
     */
    Book toDomain(BookEntity bookEntity);

}