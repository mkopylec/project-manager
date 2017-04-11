package com.github.mkopylec.projectmanager.domain.exceptions;

import org.springframework.http.HttpStatus;

public abstract class DomainException extends RuntimeException {

    private ErrorCode code;
    private HttpStatus status;

    DomainException(String message, ErrorCode code, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public ErrorCode getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
