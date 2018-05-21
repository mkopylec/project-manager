package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.common.DomainException;

import static com.github.mkopylec.projectmanager.core.team.ErrorCode.TEAM_ALREADY_EXISTS;

public class TeamAlreadyExistsException extends DomainException {

    TeamAlreadyExistsException(String message) {
        super(message, TEAM_ALREADY_EXISTS);
    }
}
