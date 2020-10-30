package com.github.mkopylec.projectmanager.api.exception;

import com.github.mkopylec.projectmanager.api.dto.Failure;
import org.slf4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static java.util.List.of;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
class ExceptionMapper {

    private static final Logger log = getLogger(ExceptionMapper.class);

    @ExceptionHandler(ApplicationException.class)
    ResponseEntity<Failure> mapToResponse(ApplicationException ex, HttpServletRequest request) {
        return mapToResponse(ex, ex.getViolation().get(), ex.getResponseStatus(), request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Failure> mapToResponse(ConstraintViolationException ex, HttpServletRequest request) {
        return mapToResponse(ex, ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(toList()), UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Failure> mapToResponse(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return mapToResponse(ex, ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(toList()), UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<Failure> mapToResponse(Exception ex, HttpServletRequest request) {
        return mapToResponse(ex, "UNEXPECTED_ERROR", INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<Failure> mapToResponse(Exception ex, String violation, HttpStatus status, HttpServletRequest request) {
        return mapToResponse(ex, of(violation), status, request);
    }

    private ResponseEntity<Failure> mapToResponse(Exception ex, List<String> violations, HttpStatus status, HttpServletRequest request) {
        var requestLog = createRequestLog(request, violations, status);
        if (status.is5xxServerError()) {
            log.error(requestLog, ex);
        } else {
            log.info(requestLog, ex);
        }
        return status(status)
                .body(new Failure().setViolations(violations));
    }

    private String createRequestLog(HttpServletRequest request, List<String> violations, HttpStatus status) {
        return request.getMethod() + " " + request.getRequestURI() + " " + status.value() + ": " + violations;
    }
}