package com.codeelevate.books.application.advice;

import com.codeelevate.books.application.dto.error.ErrorDTO;
import com.codeelevate.books.domain.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleBookNotFoundException(BookNotFoundException ex) {
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

}