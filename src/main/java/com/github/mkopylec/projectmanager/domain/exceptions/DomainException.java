package com.github.mkopylec.projectmanager.domain.exceptions;

public abstract class DomainException extends RuntimeException {

    private ErrorCode code;

    DomainException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
