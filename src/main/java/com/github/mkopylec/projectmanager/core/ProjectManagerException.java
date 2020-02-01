package com.github.mkopylec.projectmanager.core;

import com.github.mkopylec.projectmanager.core.common.RequirementsValidationException;

public class ProjectManagerException extends RuntimeException {

    private Failure failure;

    static ProjectManagerException projectManagerException(Exception e, String errorMessage) {
        Failure failure = new Failure().setMessage(errorMessage);
        if (e instanceof RequirementsValidationException) {
            failure.setCodes(((RequirementsValidationException) e).mapErrorCodes(Enum::name));
        } else {
            failure.setUnexpectedErrorCode();
        }
        return new ProjectManagerException(failure, e);
    }

    private ProjectManagerException(Failure failure, Throwable cause) {
        super(failure.toString(), cause);
        this.failure = failure;
    }

    public Failure getFailure() {
        return failure;
    }
}
