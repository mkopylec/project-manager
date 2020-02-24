package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.common.Validator;

import static com.github.mkopylec.projectmanager.core.project.AssignedTeamErrorCode.EMPTY_ASSIGNED_TEAM_NAME;
import static com.github.mkopylec.projectmanager.core.project.AssignedTeamErrorCode.INVALID_NUMBER_OF_CURRENTLY_IMPLEMENTED_PROJECT_BY_ASSIGNED_TEAM;
import static com.github.mkopylec.projectmanager.core.project.AssignedTeamErrorCode.MISSING_ASSIGNED_TEAM;

class AssignedTeamRequirementsValidator extends Validator<AssignedTeamErrorCode> {

    static AssignedTeamRequirementsValidator assignedTeamRequirements() {
        return new AssignedTeamRequirementsValidator();
    }

    private AssignedTeamRequirementsValidator() {
        super(ProjectException::new);
    }

    AssignedTeamRequirementsValidator require(AssignedTeam team) {
        return require(team, MISSING_ASSIGNED_TEAM);
    }

    AssignedTeamRequirementsValidator requireName(String name) {
        return require(name, EMPTY_ASSIGNED_TEAM_NAME);
    }

    AssignedTeamRequirementsValidator requireValidCurrentlyImplementedProjects(int currentlyImplementedProjects) {
        if (currentlyImplementedProjects < 0) {
            addError(INVALID_NUMBER_OF_CURRENTLY_IMPLEMENTED_PROJECT_BY_ASSIGNED_TEAM);
        }
        return this;
    }
}
