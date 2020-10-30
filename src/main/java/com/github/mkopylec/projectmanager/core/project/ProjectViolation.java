package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.api.exception.ApplicationViolation;

enum ProjectViolation implements ApplicationViolation {

    EMPTY_PROJECT_IDENTIFIER,
    EMPTY_PROJECT_NAME,
    EMPTY_PROJECT_STATUS,
    EMPTY_PROJECT_FEATURE,
    EMPTY_FEATURE_NAME,
    EMPTY_FEATURE_STATUS,
    EMPTY_FEATURE_REQUIREMENT,
    INVALID_STATUS,
    INVALID_REQUIREMENT,
    MISSING_PROJECT,
    PROJECT_STATUS_DIFFERENT_THAN_TO_DO,
    PROJECT_STATUS_DIFFERENT_THAN_IN_PROGRESS,
    UNDONE_PROJECT_NECESSARY_FEATURE,
    UNDONE_PROJECT_FEATURE,
    EMPTY_TEAM_ASSIGNED_TO_PROJECT;

    @Override
    public String get() {
        return name();
    }
}
