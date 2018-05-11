package com.github.mkopylec.projectmanager.domain.exceptions;

public class InvalidEntityException extends DomainException {

    InvalidEntityException(String message, ErrorCode code) {
        super(message, code);
    }
}