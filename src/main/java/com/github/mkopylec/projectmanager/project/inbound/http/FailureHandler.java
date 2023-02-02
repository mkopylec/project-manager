package com.github.mkopylec.projectmanager.project.inbound.http;

import com.github.mkopylec.projectmanager.common.inbound.http.FailureResponseHandler;
import com.github.mkopylec.projectmanager.project.core.ProjectUseCaseViolation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.FailureBody;
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.FailureCodeBody.UNEXPECTED_ERROR;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestControllerAdvice
class FailureHandler extends FailureResponseHandler {

    @ExceptionHandler(ProjectUseCaseViolation.class)
    ResponseEntity<FailureBody> handle(ProjectUseCaseViolation violation, HttpServletRequest request) {
        HttpStatus status = switch (violation.getCode()) {
            case CONCURRENT_PROJECT_MODIFICATION -> CONFLICT;
            case INVALID_COMPLETION_STATUS -> UNPROCESSABLE_ENTITY;
            case INVALID_FEATURE_NAME -> UNPROCESSABLE_ENTITY;
            case INVALID_FEATURE_REQUIREMENT -> UNPROCESSABLE_ENTITY;
            case INVALID_PROJECT_ID -> UNPROCESSABLE_ENTITY;
            case INVALID_PROJECT_NAME -> UNPROCESSABLE_ENTITY;
            case INVALID_PROJECT_FEATURES_STATE -> CONFLICT;
            case NO_PROJECT_EXISTS -> NOT_FOUND;
            case NO_TEAM_ASSIGNED_TO_PROJECT -> CONFLICT;
            case PROJECT_ALREADY_STARTED -> CONFLICT;
            case PROJECT_NOT_STARTED -> CONFLICT;
        };
        return handle(new FailureBody(violation), violation, request, status);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<FailureBody> handle(Exception exception, HttpServletRequest request) {
        return handle(new FailureBody(UNEXPECTED_ERROR), exception, request, INTERNAL_SERVER_ERROR);
    }
}
