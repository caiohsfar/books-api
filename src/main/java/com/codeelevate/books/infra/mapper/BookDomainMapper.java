package com.codeelevate.books.infra.mapper;

import com.codeelevate.books.domain.model.Book;
import com.codeelevate.books.infra.database.entity.BookEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookDomainMapper {

    Book toDomain(BookEntity bookEntity);

}