package com.github.mkopylec.projectmanager.presentation;

import com.github.mkopylec.projectmanager.core.Failure;
import com.github.mkopylec.projectmanager.core.ProjectManagerException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
class ErrorHandler {

    private static final Logger log = getLogger(ErrorHandler.class);

    @ExceptionHandler(ProjectManagerException.class)
    ResponseEntity<Failure> mapToResponse(ProjectManagerException ex, HttpServletRequest request) {
        HttpStatus status = getStatus(ex);
        log(ex, status, request);
        return status(status)
                .body(ex.getFailure());
    }

    private HttpStatus getStatus(ProjectManagerException ex) {
        Failure failure = ex.getFailure();
        if (failure.containsUnexpectedErrorCode()) {
            return INTERNAL_SERVER_ERROR;
        }
        if (failure.containsAnyCode("MISSING_PROJECT", "MISSING_TEAM")) {
            return NOT_FOUND;
        }
        return UNPROCESSABLE_ENTITY;
    }

    private void log(ProjectManagerException ex, HttpStatus status, HttpServletRequest request) {
        if (ex.getFailure().containsUnexpectedErrorCode()) {
            log.error(createRequestLog(request, INTERNAL_SERVER_ERROR), ex);
        } else {
            log.info(createRequestLog(request, status), ex);
        }
    }

    private String createRequestLog(HttpServletRequest request, HttpStatus status) {
        return request.getMethod() + " " + request.getRequestURI() + " " + status.value();
    }
}