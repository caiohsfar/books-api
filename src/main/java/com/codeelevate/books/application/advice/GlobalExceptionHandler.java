package com.codeelevate.books.application.advice;

import com.codeelevate.books.application.dto.error.ErrorDTO;
import com.codeelevate.books.domain.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Global exception handler for the Books API.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles BookNotFoundException.
     *
     * @param exception the exception
     * @return the error response
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleBookNotFoundException(BookNotFoundException exception) {
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.NOT_FOUND.value(), exception.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    /**
     * Handles generic exceptions.
     *
     * @param exception the exception
     * @return the error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGenericException(Exception exception) {
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

}