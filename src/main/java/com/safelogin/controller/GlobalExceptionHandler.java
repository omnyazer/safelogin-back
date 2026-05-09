package com.safelogin.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.safelogin.dto.AuthResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AuthResponse> handleValidationErrors(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String message = fieldErrors.isEmpty()
                ? "Données invalides."
                : fieldErrors.get(0).getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new AuthResponse(false, message));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<AuthResponse> handleUnreadableBody() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new AuthResponse(false, "Corps de requête invalide."));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<AuthResponse> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new AuthResponse(false, exception.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<AuthResponse> handleNoSuchElement(NoSuchElementException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new AuthResponse(false, exception.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<AuthResponse> handleAuthenticationException() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new AuthResponse(false, "Authentification requise."));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<AuthResponse> handleAccessDeniedException() {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new AuthResponse(false, "Accès refusé."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthResponse> handleGenericError() {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new AuthResponse(false, "Une erreur interne est survenue."));
    }
}
