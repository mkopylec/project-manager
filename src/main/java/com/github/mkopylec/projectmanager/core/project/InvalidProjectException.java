package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.common.DomainException;

public class InvalidProjectException extends DomainException {

    InvalidProjectException(String message, ErrorCode code) {
        super(message, code);
    }
}
