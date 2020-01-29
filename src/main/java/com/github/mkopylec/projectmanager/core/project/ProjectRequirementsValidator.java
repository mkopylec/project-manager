package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.common.RequirementsValidator;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.neverNull;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.EMPTY_PROJECT_FEATURE;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.EMPTY_PROJECT_FEATURE_NAME;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.EMPTY_PROJECT_FEATURE_REQUIREMENT;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.EMPTY_PROJECT_FEATURE_STATUS;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.EMPTY_PROJECT_IDENTIFIER;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.EMPTY_PROJECT_NAME;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.EMPTY_PROJECT_STATUS;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.EMPTY_TEAM_ASSIGNED_TO_PROJECT;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.MISSING_PROJECT;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.PROJECT_STATUS_DIFFERENT_THAN_IN_PROGRESS;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.PROJECT_STATUS_DIFFERENT_THAN_TO_DO;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.UNDONE_PROJECT_FEATURE;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.UNDONE_PROJECT_NECESSARY_FEATURE;
import static com.github.mkopylec.projectmanager.core.project.Status.IN_PROGRESS;
import static com.github.mkopylec.projectmanager.core.project.Status.TO_DO;

class ProjectRequirementsValidator extends RequirementsValidator {

    static ProjectRequirementsValidator requirements() {
        return new ProjectRequirementsValidator();
    }

    private ProjectRequirementsValidator() {
        super(ProjectException::new);
    }

    ProjectRequirementsValidator requireProject(Project project, String message) {
        if (isEmpty(project)) {
            addError(MISSING_PROJECT, message);
        }
        return this;
    }

    ProjectRequirementsValidator requireIdentifier(String identifier, String message) {
        if (isEmpty(identifier)) {
            addError(EMPTY_PROJECT_IDENTIFIER, message);
        }
        return this;
    }

    ProjectRequirementsValidator requireName(String name, String message) {
        if (isEmpty(name)) {
            addError(EMPTY_PROJECT_NAME, message);
        }
        return this;
    }

    ProjectRequirementsValidator requireValidStatus(Status status, String message) {
        if (isEmpty(status)) {
            addError(EMPTY_PROJECT_STATUS, message);
        }
        return this;
    }

    ProjectRequirementsValidator requireValidFeatures(List<Feature> features, String message) {
        neverNull(features).forEach(feature -> {
            if (isEmpty(feature)) {
                addError(EMPTY_PROJECT_FEATURE, message);
            } else if (feature.isUnnamed()) {
                addError(EMPTY_PROJECT_FEATURE_NAME, message);
            } else if (feature.hasNoStatus()) {
                addError(EMPTY_PROJECT_FEATURE_STATUS, message);
            } else if (feature.hasNoRequirement()) {
                addError(EMPTY_PROJECT_FEATURE_REQUIREMENT, message);
            }
        });
        return this;
    }

    ProjectRequirementsValidator requireNecessaryFeaturesDone(List<Feature> features, String message) {
        neverNull(features).forEach(feature -> {
            if (isEmpty(feature)) {
                addError(EMPTY_PROJECT_FEATURE, message);
            } else if (feature.isNecessaryAndUndone()) {
                addError(UNDONE_PROJECT_NECESSARY_FEATURE, message);
            }
        });
        return this;
    }

    ProjectRequirementsValidator requireAllFeaturesDone(List<Feature> features, String message) {
        neverNull(features).forEach(feature -> {
            if (isEmpty(feature)) {
                addError(EMPTY_PROJECT_FEATURE, message);
            } else if (feature.isUndone()) {
                addError(UNDONE_PROJECT_FEATURE, message);
            }
        });
        return this;
    }

    ProjectRequirementsValidator requireAssignedTeam(String assignedTeam, String message) {
        if (isEmpty(assignedTeam)) {
            addError(EMPTY_TEAM_ASSIGNED_TO_PROJECT, message);
        }
        return this;
    }

    ProjectRequirementsValidator requireToDoStatus(Status status, String message) {
        if (status != TO_DO) {
            addError(PROJECT_STATUS_DIFFERENT_THAN_TO_DO, message);
        }
        return this;
    }

    ProjectRequirementsValidator requireInProgressStatus(Status status, String message) {
        if (status != IN_PROGRESS) {
            addError(PROJECT_STATUS_DIFFERENT_THAN_IN_PROGRESS, message);
        }
        return this;
    }
}
