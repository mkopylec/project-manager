package com.github.mkopylec.projectmanager.infrastructure.error;

import javax.servlet.http.HttpServletRequest;

import com.github.mkopylec.projectmanager.domain.exceptions.DomainException;
import com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode;
import org.slf4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.UNEXPECTED_ERROR;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
class ErrorHandler {

    private static final Logger log = getLogger(ErrorHandler.class);

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorMessage> handleDomainException(DomainException ex, HttpServletRequest request) {
        log.warn(createLog(request, ex.getStatus(), ex.getCode(), ex.getMessage()));
        return status(ex.getStatus())
                .body(new ErrorMessage(ex.getCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception ex, HttpServletRequest request) {
        log.error(createLog(request, INTERNAL_SERVER_ERROR, UNEXPECTED_ERROR, ex.getMessage()), ex);
        return status(INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(UNEXPECTED_ERROR));
    }

    private String createLog(HttpServletRequest request, HttpStatus status, ErrorCode code, String message) {
        return request.getMethod() + " " + request.getRequestURI() + " " + status.value() + " | " + code + " | " + message;
    }
}
