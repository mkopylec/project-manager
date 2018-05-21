package com.github.mkopylec.projectmanager.api;

import com.github.mkopylec.projectmanager.core.common.DomainException;
import com.github.mkopylec.projectmanager.core.common.ErrorMessage;
import com.github.mkopylec.projectmanager.core.project.InvalidProjectException;
import com.github.mkopylec.projectmanager.core.project.MissingProjectException;
import com.github.mkopylec.projectmanager.core.team.InvalidTeamException;
import com.github.mkopylec.projectmanager.core.team.MissingTeamException;
import com.github.mkopylec.projectmanager.core.team.TeamAlreadyExistsException;
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

    @ExceptionHandler({TeamAlreadyExistsException.class, InvalidTeamException.class, InvalidProjectException.class})
    ResponseEntity<ErrorMessage> mapToUnprocessableEntityResponse(DomainException ex, HttpServletRequest request) {
        return mapToResponse(ex, request, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({MissingTeamException.class, MissingProjectException.class})
    ResponseEntity<ErrorMessage> mapToNotFoundResponse(DomainException ex, HttpServletRequest request) {
        return mapToResponse(ex, request, NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorMessage> mapToInternalServerErrorResponse(Exception ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(UNEXPECTED_ERROR_CODE);
        log.error(createLog(request, INTERNAL_SERVER_ERROR, errorMessage, ex.getMessage()), ex);
        return status(INTERNAL_SERVER_ERROR)
                .body(errorMessage);
    }

    private ResponseEntity<ErrorMessage> mapToResponse(DomainException ex, HttpServletRequest request, HttpStatus status) {
        log.warn(createLog(request, status, ex.getErrorMessage(), ex.getMessage()));
        return status(status)
                .body(ex.getErrorMessage());
    }

    private String createLog(HttpServletRequest request, HttpStatus status, ErrorMessage errorMessage, String message) {
        return request.getMethod() + " " + request.getRequestURI() + " " + status.value() + " | " + errorMessage.getCode() + " | " + message;
    }
}