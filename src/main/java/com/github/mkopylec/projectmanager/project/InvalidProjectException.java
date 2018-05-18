package com.github.mkopylec.projectmanager.project;

import com.github.mkopylec.projectmanager.errors.DomainException;

public class InvalidProjectException extends DomainException {

    InvalidProjectException(String message, ErrorCode code) {
        super(message, code.name());
    }
}
