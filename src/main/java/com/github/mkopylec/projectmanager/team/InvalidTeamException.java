package com.github.mkopylec.projectmanager.team;

import com.github.mkopylec.projectmanager.errors.DomainException;

public class InvalidTeamException extends DomainException {

    InvalidTeamException(String message, ErrorCode code) {
        super(message, code.name());
    }
}
