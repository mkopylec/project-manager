package com.github.mkopylec.projectmanager.project.core;

import com.github.mkopylec.projectmanager.common.core.UseCaseViolation;
import com.github.mkopylec.projectmanager.project.core.OutgoingDto.ViolationCode;

import static com.github.mkopylec.projectmanager.project.core.OutgoingDto.ViolationCode.violationCode;

public abstract sealed class ProjectUseCaseViolation extends UseCaseViolation permits ProjectService.ProjectDraftNotCreated, ProjectService.ProjectDraftsNotLoaded, ProjectService.ProjectNotCreated, ProjectService.ProjectNotEnded, ProjectService.ProjectNotLoaded, ProjectService.ProjectNotStarted, ProjectService.ProjectNotUpdated {

    ProjectUseCaseViolation(ProjectBusinessRuleViolation violation) {
        super(violationCode(violation), violation);
    }

    public ViolationCode getCode() {
        return super.getCode(ViolationCode.class);
    }
}
