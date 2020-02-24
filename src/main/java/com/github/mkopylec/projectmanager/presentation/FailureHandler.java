package com.github.mkopylec.projectmanager.presentation;

import com.github.mkopylec.projectmanager.core.common.ProjectManagerException;
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
class FailureHandler {

    private static final Logger log = getLogger(FailureHandler.class);

    @ExceptionHandler(ProjectManagerException.class)
    ResponseEntity<Failure> mapToResponse(ProjectManagerException ex, HttpServletRequest request) {
        var status = getStatus(ex);
        log(ex, status, request);
        var failure = new Failure(ex.mapValidationErrorCodes(Enum::name), ex.getMessage());
        return status(status)
                .body(failure);
    }

    private HttpStatus getStatus(ProjectManagerException ex) {
        if (ex.indicatesInvalidEntity()) {
            return UNPROCESSABLE_ENTITY;
        }
        if (ex.indicatesMissingEntity()) {
            return NOT_FOUND;
        }
        return INTERNAL_SERVER_ERROR;
    }

    private void log(ProjectManagerException ex, HttpStatus status, HttpServletRequest request) {
        var requestLog = createRequestLog(request, status);
        if (ex.indicatesError()) {
            log.error(requestLog, ex);
        } else {
            log.info(requestLog, ex);
        }
    }

    private String createRequestLog(HttpServletRequest request, HttpStatus status) {
        return request.getMethod() + " " + request.getRequestURI() + " " + status.value();
    }
}