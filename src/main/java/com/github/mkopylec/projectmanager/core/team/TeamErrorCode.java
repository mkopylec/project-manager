package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.common.ValidationErrorCode;

enum TeamErrorCode implements ValidationErrorCode {

    TEAM_EXISTS,
    MISSING_TEAM,
    EMPTY_TEAM_NAME,
    INVALID_NUMBER_OF_CURRENTLY_IMPLEMENTED_PROJECT_BY_TEAM,
    EMPTY_TEAM_MEMBER,
    EMPTY_TEAM_MEMBER_FIRST_NAME,
    EMPTY_TEAM_MEMBER_LAST_NAME,
    EMPTY_TEAM_MEMBER_JOB_POSITION
}
