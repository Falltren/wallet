package com.fallt.wallet.controller.advice;

import com.fallt.wallet.domain.dto.response.ErrorResponse;
import com.fallt.wallet.exception.EntityNotFoundException;
import com.fallt.wallet.exception.NotHaveEnoughFunds;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(Exception e) {
        ErrorResponse body = com.fallt.wallet.domain.dto.response.ErrorResponse.builder()
                .timestamp(System.currentTimeMillis())
                .errorDescription(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(body);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, NotHaveEnoughFunds.class})
    public ResponseEntity<ErrorResponse> handleValidationException(Exception ex) {
        String cause = (ex instanceof MethodArgumentNotValidException exception) ?
                Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage() : ex.getMessage();
        ErrorResponse body = ErrorResponse.builder()
                .timestamp(System.currentTimeMillis())
                .errorDescription(cause)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

}
