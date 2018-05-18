package com.github.mkopylec.projectmanager.project;

import com.github.mkopylec.projectmanager.errors.DomainPreCondition;

class PreCondition extends DomainPreCondition {

    protected PreCondition(boolean condition) {
        super(condition);
    }

    static PreCondition when(boolean condition) {
        return new PreCondition(condition);
    }

    void thenInvalidProject(ErrorCode code, String message) {
        thenThrow(() -> new InvalidProjectException(message, code));
    }

    void thenMissingProject(ErrorCode code, String message) {
        thenThrow(() -> new MissingProjectException(message, code));
    }
}
