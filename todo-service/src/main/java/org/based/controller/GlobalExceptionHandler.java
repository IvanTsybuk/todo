package org.based.controller;

import java.time.LocalDateTime;
import lombok.extern.log4j.Log4j2;
import org.based.exceptions.EntityAlreadyExistsException;
import org.based.exceptions.EntityNotFoundException;
import org.based.exceptions.ExceptionResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    @NotNull
    public ResponseEntity<ExceptionResponse> notFoundException(
            @NotNull EntityNotFoundException e) {
        log.debug(String.format("NotFoundException. Message: %s, status: %s",
                e.getMessage(), HttpStatus.NOT_FOUND));
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(),
                LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EntityAlreadyExistsException.class)
    @NotNull
    public ResponseEntity<ExceptionResponse> conflictException(
            @NotNull EntityAlreadyExistsException e) {
        log.debug(String.format("ConflictException. Message: %s, status: %s",
                e.getMessage(), HttpStatus.CONFLICT));
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(),
                LocalDateTime.now()), HttpStatus.CONFLICT);
    }
}
