package com.github.mkopylec.projectmanager.domain.exceptions;

public class MissingEntityException extends DomainException {

    MissingEntityException(String message, ErrorCode code) {
        super(message, code);
    }
}
