package com.github.mkopylec.projectmanager.errors;

import com.github.mkopylec.projectmanager.project.InvalidProjectException;
import com.github.mkopylec.projectmanager.project.MissingProjectException;
import com.github.mkopylec.projectmanager.team.InvalidTeamException;
import com.github.mkopylec.projectmanager.team.MissingTeamException;
import com.github.mkopylec.projectmanager.team.TeamAlreadyExistsException;
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

    private static final String UNEXPECTED_ERROR_CODE = "UNEXPECTED_ERROR";

    private static final Logger log = getLogger(ErrorHandler.class);

    @ExceptionHandler(TeamAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleTeamAlreadyExistsException(TeamAlreadyExistsException ex, HttpServletRequest request) {
        return handleDomainException(ex, request, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidTeamException.class)
    public ResponseEntity<ErrorMessage> handleInvalidTeamException(InvalidTeamException ex, HttpServletRequest request) {
        return handleDomainException(ex, request, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidProjectException.class)
    public ResponseEntity<ErrorMessage> handleInvalidProjectException(InvalidProjectException ex, HttpServletRequest request) {
        return handleDomainException(ex, request, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(MissingTeamException.class)
    public ResponseEntity<ErrorMessage> handleMissingTeamException(MissingTeamException ex, HttpServletRequest request) {
        return handleDomainException(ex, request, NOT_FOUND);
    }

    @ExceptionHandler(MissingProjectException.class)
    public ResponseEntity<ErrorMessage> handleMissingProjectException(MissingProjectException ex, HttpServletRequest request) {
        return handleDomainException(ex, request, NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception ex, HttpServletRequest request) {
        log.error(createLog(request, INTERNAL_SERVER_ERROR, UNEXPECTED_ERROR_CODE, ex.getMessage()), ex);
        return status(INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(UNEXPECTED_ERROR_CODE));
    }

    private ResponseEntity<ErrorMessage> handleDomainException(DomainException ex, HttpServletRequest request, HttpStatus status) {
        log.warn(createLog(request, status, ex.getCode(), ex.getMessage()));
        return status(status)
                .body(new ErrorMessage(ex.getCode()));
    }

    private String createLog(HttpServletRequest request, HttpStatus status, String code, String message) {
        return request.getMethod() + " " + request.getRequestURI() + " " + status.value() + " | " + code + " | " + message;
    }
}