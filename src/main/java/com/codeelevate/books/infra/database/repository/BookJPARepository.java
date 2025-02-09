package com.codeelevate.books.infra.database.repository;

import com.codeelevate.books.infra.database.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookJPARepository extends JpaRepository<BookEntity, Long> {

}