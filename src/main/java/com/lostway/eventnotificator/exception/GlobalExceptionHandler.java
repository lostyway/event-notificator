package com.lostway.eventnotificator.exception;

import com.lostway.eventnotificator.exception.dto.ErrorMessageResponse;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleNoResourceException(NoResourceFoundException e) {
        log.error("Exception handled:", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessageResponse(
                        "Данный адрес не был найден",
                        e.getMessage(),
                        now()
                ));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorMessageResponse> handleJwtException(JwtException e) {
        log.error("Exception handled:", e);
        return ResponseEntity.status(401)
                .body(new ErrorMessageResponse(
                        "Сущность не найдена",
                        e.getMessage(),
                        now()
                ));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorMessageResponse> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        log.error("Exception handled:", e);
        return ResponseEntity.status(403)
                .body(new ErrorMessageResponse(
                        "Недостаточно прав для выполнения операции",
                        e.getMessage(),
                        now()
                ));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorMessageResponse> handleAllException(Throwable e) {
        log.error("Exception handled:", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessageResponse(
                        "Внутренняя ошибка сервера",
                        e.getMessage(),
                        now()
                ));
    }

    private String getDetailedMessage(BindException ex) {
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format("Поле '%s' : %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining("; "));
    }

    private static List<String> getDetails(HandlerMethodValidationException ex) {
        return ex.getParameterValidationResults().stream()
                .flatMap(r -> r.getResolvableErrors().stream())
                .map(error -> String.format("Field: %s, Message: %s",
                        Arrays.toString(error.getArguments()), error.getDefaultMessage()))
                .toList();
    }
}
