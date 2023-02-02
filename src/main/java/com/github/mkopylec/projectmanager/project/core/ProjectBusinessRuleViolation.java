package com.github.mkopylec.projectmanager.project.core;

import com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation;
import com.github.mkopylec.projectmanager.common.core.BusinessRuleViolationProperties;
import com.github.mkopylec.projectmanager.project.core.ProjectName.InvalidProjectName;

import static com.github.mkopylec.projectmanager.project.core.CompletionStatus.InvalidCompletionStatus;
import static com.github.mkopylec.projectmanager.project.core.FeatureName.InvalidFeatureName;
import static com.github.mkopylec.projectmanager.project.core.FeatureRequirement.InvalidFeatureRequirement;
import static com.github.mkopylec.projectmanager.project.core.Project.InvalidProjectFeaturesState;
import static com.github.mkopylec.projectmanager.project.core.Project.ProjectAlreadyStarted;
import static com.github.mkopylec.projectmanager.project.core.Project.ProjectNotStarted;
import static com.github.mkopylec.projectmanager.project.core.ProjectId.InvalidProjectId;
import static com.github.mkopylec.projectmanager.project.core.ProjectRepository.ConcurrentProjectModification;
import static com.github.mkopylec.projectmanager.project.core.ProjectRepository.NoProjectExists;
import static com.github.mkopylec.projectmanager.project.core.TeamAssignedToProject.NoTeamAssignedToProject;

abstract sealed class ProjectBusinessRuleViolation extends BusinessRuleViolation permits InvalidCompletionStatus, InvalidFeatureName, InvalidFeatureRequirement, InvalidProjectFeaturesState, ProjectAlreadyStarted, ProjectNotStarted, InvalidProjectId, InvalidProjectName, ConcurrentProjectModification, NoProjectExists, NoTeamAssignedToProject {

    ProjectBusinessRuleViolation(BusinessRuleViolationProperties properties) {
        super(properties);
    }

    ProjectBusinessRuleViolation(BusinessRuleViolationProperties properties, Exception cause) {
        super(properties, cause);
    }
}
