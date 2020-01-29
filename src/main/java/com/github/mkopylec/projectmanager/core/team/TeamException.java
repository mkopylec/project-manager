package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.common.ProjectManagerException;
import com.github.mkopylec.projectmanager.core.common.ValidationError;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.team.ErrorCode.MISSING_TEAM;

public class TeamException extends ProjectManagerException {

    TeamException(List<ValidationError> validationErrors) {
        super(MISSING_TEAM, validationErrors);
    }
}
