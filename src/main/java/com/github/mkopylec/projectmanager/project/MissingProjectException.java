package com.github.mkopylec.projectmanager.project;

import com.github.mkopylec.projectmanager.errors.DomainException;

public class MissingProjectException extends DomainException {

    MissingProjectException(String message, ErrorCode code) {
        super(message, code.name());
    }
}
