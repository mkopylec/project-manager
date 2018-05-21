package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.common.DomainException;

import static com.github.mkopylec.projectmanager.core.team.ErrorCode.MISSING_TEAM;

public class MissingTeamException extends DomainException {

    MissingTeamException(String message) {
        super(message, MISSING_TEAM);
    }
}
