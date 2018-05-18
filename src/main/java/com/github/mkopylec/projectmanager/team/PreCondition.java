package com.github.mkopylec.projectmanager.team;

import com.github.mkopylec.projectmanager.errors.DomainPreCondition;

class PreCondition extends DomainPreCondition {

    protected PreCondition(boolean condition) {
        super(condition);
    }

    static PreCondition when(boolean condition) {
        return new PreCondition(condition);
    }

    void thenInvalidTeam(ErrorCode code, String message) {
        thenThrow(() -> new InvalidTeamException(message, code));
    }

    void thenMissingTeam(ErrorCode code, String message) {
        thenThrow(() -> new MissingTeamException(message, code));
    }

    void thenTeamAlreadyExists(ErrorCode code, String message) {
        thenThrow(() -> new TeamAlreadyExistsException(message, code));
    }
}
