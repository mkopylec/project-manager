package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.common.ValidationErrorCode;

enum AssignedTeamErrorCode implements ValidationErrorCode {

    MISSING_ASSIGNED_TEAM,
    INVALID_NUMBER_OF_CURRENTLY_IMPLEMENTED_PROJECT_BY_ASSIGNED_TEAM,
    EMPTY_ASSIGNED_TEAM_NAME
}
