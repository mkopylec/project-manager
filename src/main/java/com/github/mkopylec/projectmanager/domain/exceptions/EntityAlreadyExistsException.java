package com.github.mkopylec.projectmanager.domain.exceptions;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

class EntityAlreadyExistsException extends DomainException {

    EntityAlreadyExistsException(String message, ErrorCode code) {
        super(message, code, UNPROCESSABLE_ENTITY);
    }
}
