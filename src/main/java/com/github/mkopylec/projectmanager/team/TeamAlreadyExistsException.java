package com.github.mkopylec.projectmanager.team;

import com.github.mkopylec.projectmanager.errors.DomainException;

public class TeamAlreadyExistsException extends DomainException {

    TeamAlreadyExistsException(String message, ErrorCode code) {
        super(message, code.name());
    }
}
