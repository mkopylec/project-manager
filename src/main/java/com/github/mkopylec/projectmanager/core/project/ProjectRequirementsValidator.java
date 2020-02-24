package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.common.Validator;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.neverNull;
import static com.github.mkopylec.projectmanager.core.project.ProjectErrorCode.EMPTY_PROJECT_ASSIGNED_TEAM;
import static com.github.mkopylec.projectmanager.core.project.ProjectErrorCode.EMPTY_PROJECT_FEATURE;
import static com.github.mkopylec.projectmanager.core.project.ProjectErrorCode.EMPTY_PROJECT_FEATURE_NAME;
import static com.github.mkopylec.projectmanager.core.project.ProjectErrorCode.EMPTY_PROJECT_FEATURE_REQUIREMENT;
import static com.github.mkopylec.projectmanager.core.project.ProjectErrorCode.EMPTY_PROJECT_FEATURE_STATUS;
import static com.github.mkopylec.projectmanager.core.project.ProjectErrorCode.EMPTY_PROJECT_IDENTIFIER;
import static com.github.mkopylec.projectmanager.core.project.ProjectErrorCode.EMPTY_PROJECT_NAME;
import static com.github.mkopylec.projectmanager.core.project.ProjectErrorCode.EMPTY_PROJECT_STATUS;
import static com.github.mkopylec.projectmanager.core.project.ProjectErrorCode.MISSING_PROJECT;
import static com.github.mkopylec.projectmanager.core.project.ProjectErrorCode.PROJECT_STATUS_DIFFERENT_THAN_IN_PROGRESS;
import static com.github.mkopylec.projectmanager.core.project.ProjectErrorCode.PROJECT_STATUS_DIFFERENT_THAN_TO_DO;
import static com.github.mkopylec.projectmanager.core.project.ProjectErrorCode.UNDONE_PROJECT_FEATURE;
import static com.github.mkopylec.projectmanager.core.project.ProjectErrorCode.UNDONE_PROJECT_NECESSARY_FEATURE;
import static com.github.mkopylec.projectmanager.core.project.Status.IN_PROGRESS;
import static com.github.mkopylec.projectmanager.core.project.Status.TO_DO;

class ProjectRequirementsValidator extends Validator<ProjectErrorCode> {

    static ProjectRequirementsValidator projectRequirements() {
        return new ProjectRequirementsValidator();
    }

    private ProjectRequirementsValidator() {
        super(ProjectException::new);
    }

    ProjectRequirementsValidator require(Project project) {
        return require(project, MISSING_PROJECT);
    }

    ProjectRequirementsValidator requireIdentifier(String identifier) {
        return require(identifier, EMPTY_PROJECT_IDENTIFIER);
    }

    ProjectRequirementsValidator requireName(String name) {
        return require(name, EMPTY_PROJECT_NAME);
    }

    ProjectRequirementsValidator requireStatus(Status status) {
        return require(status, EMPTY_PROJECT_STATUS);
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
        return require(assignedTeam, EMPTY_PROJECT_ASSIGNED_TEAM);
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
