package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.common.ValidationErrorCode;
import com.github.mkopylec.projectmanager.core.common.ValidationException;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.team.TeamErrorCode.MISSING_TEAM;

class TeamException extends ValidationException {

    TeamException(List<? extends Enum<? extends ValidationErrorCode>> errorCodes) {
        super(errorCodes);
    }

    @Override
    protected boolean indicatesMissingEntity() {
        return containsErrorCode(MISSING_TEAM);
    }
}
