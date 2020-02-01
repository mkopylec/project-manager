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

    ProjectRequirementsValidator requireProject(Project project) {
        if (isEmpty(project)) {
            addError(MISSING_PROJECT);
        }
        return this;
    }

    ProjectRequirementsValidator requireIdentifier(String identifier) {
        if (isEmpty(identifier)) {
            addError(EMPTY_PROJECT_IDENTIFIER);
        }
        return this;
    }

    ProjectRequirementsValidator requireName(String name) {
        if (isEmpty(name)) {
            addError(EMPTY_PROJECT_NAME);
        }
        return this;
    }

    ProjectRequirementsValidator requireValidStatus(Status status) {
        if (isEmpty(status)) {
            addError(EMPTY_PROJECT_STATUS);
        }
        return this;
    }

    ProjectRequirementsValidator requireValidFeatures(List<Feature> features) {
        neverNull(features).forEach(feature -> {
            if (isEmpty(feature)) {
                addError(EMPTY_PROJECT_FEATURE);
            } else if (feature.isUnnamed()) {
                addError(EMPTY_PROJECT_FEATURE_NAME);
            } else if (feature.hasNoStatus()) {
                addError(EMPTY_PROJECT_FEATURE_STATUS);
            } else if (feature.hasNoRequirement()) {
                addError(EMPTY_PROJECT_FEATURE_REQUIREMENT);
            }
        });
        return this;
    }

    ProjectRequirementsValidator requireNecessaryFeaturesDone(List<Feature> features) {
        neverNull(features).forEach(feature -> {
            if (isEmpty(feature)) {
                addError(EMPTY_PROJECT_FEATURE);
            } else if (feature.isNecessaryAndUndone()) {
                addError(UNDONE_PROJECT_NECESSARY_FEATURE);
            }
        });
        return this;
    }

    ProjectRequirementsValidator requireAllFeaturesDone(List<Feature> features) {
        neverNull(features).forEach(feature -> {
            if (isEmpty(feature)) {
                addError(EMPTY_PROJECT_FEATURE);
            } else if (feature.isUndone()) {
                addError(UNDONE_PROJECT_FEATURE);
            }
        });
        return this;
    }

    ProjectRequirementsValidator requireAssignedTeam(String assignedTeam) {
        if (isEmpty(assignedTeam)) {
            addError(EMPTY_TEAM_ASSIGNED_TO_PROJECT);
        }
        return this;
    }

    ProjectRequirementsValidator requireToDoStatus(Status status) {
        if (status != TO_DO) {
            addError(PROJECT_STATUS_DIFFERENT_THAN_TO_DO);
        }
        return this;
    }

    ProjectRequirementsValidator requireInProgressStatus(Status status) {
        if (status != IN_PROGRESS) {
            addError(PROJECT_STATUS_DIFFERENT_THAN_IN_PROGRESS);
        }
        return this;
    }
}
