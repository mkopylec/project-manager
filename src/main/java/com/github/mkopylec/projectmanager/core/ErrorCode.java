package com.github.mkopylec.projectmanager.core;

import com.github.mkopylec.projectmanager.core.common.ValidationErrorCode;

enum ErrorCode implements ValidationErrorCode {

    EMPTY_PROJECT_IDENTIFIER,
    EMPTY_NEW_PROJECT,
    EMPTY_NEW_PROJECT_DRAFT,
    EMPTY_UPDATED_PROJECT,
    EMPTY_PROJECT_ENDING_CONDITION,
    EMPTY_NEW_TEAM,
    EMPTY_TEAM_NAME,
    EMPTY_NEW_TEAM_MEMBER,
}
