package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.common.DomainException;

public class InvalidTeamException extends DomainException {

    InvalidTeamException(String message, ErrorCode code) {
        super(message, code);
    }
}
