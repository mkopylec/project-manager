package com.github.mkopylec.projectmanager.domain.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

class MissingEntityException extends DomainException {

    MissingEntityException(String message, ErrorCode code) {
        super(message, code, NOT_FOUND);
    }
}
