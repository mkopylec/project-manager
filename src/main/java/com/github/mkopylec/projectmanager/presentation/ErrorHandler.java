package com.github.mkopylec.projectmanager.presentation;

import com.github.mkopylec.projectmanager.core.common.ProjectManagerException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.List.of;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
class ErrorHandler {

    private static final Logger log = getLogger(ErrorHandler.class);

    @ExceptionHandler(ProjectManagerException.class)
    ResponseEntity<List<Error>> mapToResponse(ProjectManagerException ex, HttpServletRequest request) {
        HttpStatus status = ex.indicatesMissingEntity() ? NOT_FOUND : UNPROCESSABLE_ENTITY;
        log.info(createRequestLog(request, status) + " | " + ex.getMessage());
        return status(status)
                .body(ex.mapValidationErrors(error -> new Error(error.getCode().name(), error.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<List<Error>> mapToResponse(Exception ex, HttpServletRequest request) {
        Error error = new Error("UNEXPECTED_ERROR", "An unexpected error occurred");
        log.error(createRequestLog(request, INTERNAL_SERVER_ERROR), ex);
        return status(INTERNAL_SERVER_ERROR)
                .body(of(error));
    }

    private String createRequestLog(HttpServletRequest request, HttpStatus status) {
        return request.getMethod() + " " + request.getRequestURI() + " " + status.value();
    }
}