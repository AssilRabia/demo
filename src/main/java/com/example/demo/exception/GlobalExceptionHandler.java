package com.example.demo.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * GlobalExceptionHandler
 *
 * @author Assil Rabia
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handel NotFoundException
     *
     * @param ex          NotFoundException
     * @param httpRequest HttpServletRequest
     * @return ResponseEntity
     */
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<APIError> handelNotFoundException(NotFoundException ex, HttpServletRequest httpRequest) {
        return new ResponseEntity<>(APIError.builder()
                .timestamp(LocalDateTime.now())
                .errorCode(HttpStatus.NOT_FOUND.toString())
                .errorMessage(ex.getLocalizedMessage())
                .requestType(httpRequest.getMethod())
                .requestPath(httpRequest.getRequestURI())
                .build(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handel UserDataNotValidException
     *
     * @param ex          UserDataNotValidException
     * @param httpRequest HttpServletRequest
     * @return ResponseEntity
     */
    @ExceptionHandler(value = UserDataNotValidException.class)
    public ResponseEntity<APIError> handelUserDataNotValidException(UserDataNotValidException ex, HttpServletRequest httpRequest) {
        return new ResponseEntity<>(APIError.builder()
                .timestamp(LocalDateTime.now())
                .errorCode(HttpStatus.BAD_REQUEST.toString())
                .errorMessage(ex.getMessage())
                .requestType(httpRequest.getMethod())
                .requestPath(httpRequest.getRequestURI())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @Builder
    @Data
    public static class APIError {

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
        private LocalDateTime timestamp;
        private String errorCode;
        private String errorMessage;
        private String requestType;
        private String requestPath;

    }

}
