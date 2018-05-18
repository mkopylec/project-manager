package com.github.mkopylec.projectmanager.team;

import com.github.mkopylec.projectmanager.errors.DomainException;

public class MissingTeamException extends DomainException {

    MissingTeamException(String message, ErrorCode code) {
        super(message, code.name());
    }
}
