package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.common.DomainPreCondition;

class PreCondition extends DomainPreCondition {

    private PreCondition(boolean condition) {
        super(condition);
    }

    static PreCondition when(boolean condition) {
        return new PreCondition(condition);
    }

    void thenInvalidTeam(ErrorCode code, String message) {
        thenThrow(() -> new InvalidTeamException(message, code));
    }

    void thenMissingTeam(String message) {
        thenThrow(() -> new MissingTeamException(message));
    }

    void thenTeamAlreadyExists(String message) {
        thenThrow(() -> new TeamAlreadyExistsException(message));
    }
}
