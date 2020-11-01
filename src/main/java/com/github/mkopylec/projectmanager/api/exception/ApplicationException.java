package com.github.mkopylec.projectmanager.api.exception;

import org.springframework.http.HttpStatus;

abstract class ApplicationException extends RuntimeException {

    private ApplicationViolation violation;
    private HttpStatus responseStatus;

    ApplicationException(ApplicationViolation violation, HttpStatus responseStatus) {
        super(violation.get());
        this.violation = violation;
        this.responseStatus = responseStatus;
    }

    ApplicationViolation getViolation() {
        return violation;
    }

    HttpStatus getResponseStatus() {
        return responseStatus;
    }
}
