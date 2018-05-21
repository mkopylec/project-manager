package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.common.DomainException;

import static com.github.mkopylec.projectmanager.core.project.ErrorCode.MISSING_PROJECT;

public class MissingProjectException extends DomainException {

    MissingProjectException(String message) {
        super(message, MISSING_PROJECT);
    }
}
