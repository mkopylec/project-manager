package com.github.mkopylec.projectmanager.domain.exceptions;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

class InvalidValueException extends DomainException {

    InvalidValueException(String message, ErrorCode code) {
        super(message, code, UNPROCESSABLE_ENTITY);
    }
}
