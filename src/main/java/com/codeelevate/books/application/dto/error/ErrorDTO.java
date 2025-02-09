package com.codeelevate.books.application.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorDTO {

    private int status;
    private String message;
    private LocalDateTime timestamp;

}
