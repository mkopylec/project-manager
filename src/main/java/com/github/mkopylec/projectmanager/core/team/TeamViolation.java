package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.api.exception.ApplicationViolation;

enum TeamViolation implements ApplicationViolation {

    TEAM_EXISTS,
    MISSING_TEAM,
    MISSING_TEAM_ASSIGNED_TO_PROJECT,
    EMPTY_TEAM_NAME,
    INVALID_NUMBER_OF_CURRENTLY_IMPLEMENTED_PROJECT_BY_TEAM,
    EMPTY_TEAM_MEMBER,
    EMPTY_MEMBER_FIRST_NAME,
    EMPTY_MEMBER_LAST_NAME,
    EMPTY_MEMBER_JOB_POSITION,
    INVALID_JOB_POSITION;

    @Override
    public String get() {
        return name();
    }
}
