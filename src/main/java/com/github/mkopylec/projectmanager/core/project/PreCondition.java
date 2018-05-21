package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.common.DomainPreCondition;

class PreCondition extends DomainPreCondition {

    private PreCondition(boolean condition) {
        super(condition);
    }

    static PreCondition when(boolean condition) {
        return new PreCondition(condition);
    }

    void thenInvalidProject(ErrorCode code, String message) {
        thenThrow(() -> new InvalidProjectException(message, code));
    }

    void thenMissingProject(String message) {
        thenThrow(() -> new MissingProjectException(message));
    }
}
