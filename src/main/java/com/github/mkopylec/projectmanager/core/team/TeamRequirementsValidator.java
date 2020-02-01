package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.common.RequirementsValidator;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.isNotEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.neverNull;
import static com.github.mkopylec.projectmanager.core.team.ErrorCode.EMPTY_TEAM_MEMBER;
import static com.github.mkopylec.projectmanager.core.team.ErrorCode.EMPTY_TEAM_MEMBER_FIRST_NAME;
import static com.github.mkopylec.projectmanager.core.team.ErrorCode.EMPTY_TEAM_MEMBER_JOB_POSITION;
import static com.github.mkopylec.projectmanager.core.team.ErrorCode.EMPTY_TEAM_MEMBER_LAST_NAME;
import static com.github.mkopylec.projectmanager.core.team.ErrorCode.EMPTY_TEAM_NAME;
import static com.github.mkopylec.projectmanager.core.team.ErrorCode.INVALID_NUMBER_OF_CURRENTLY_IMPLEMENTED_PROJECT_BY_TEAM;
import static com.github.mkopylec.projectmanager.core.team.ErrorCode.MISSING_TEAM;
import static com.github.mkopylec.projectmanager.core.team.ErrorCode.MISSING_TEAM_ASSIGNED_TO_PROJECT;
import static com.github.mkopylec.projectmanager.core.team.ErrorCode.TEAM_EXISTS;

class TeamRequirementsValidator extends RequirementsValidator {

    static TeamRequirementsValidator requirements() {
        return new TeamRequirementsValidator();
    }

    private TeamRequirementsValidator() {
        super(TeamException::new);
    }

    TeamRequirementsValidator requireTeam(Team team) {
        if (isEmpty(team)) {
            addError(MISSING_TEAM);
        }
        return this;
    }

    TeamRequirementsValidator requireTeamAssignedToProject(Team team) {
        if (isEmpty(team)) {
            addError(MISSING_TEAM_ASSIGNED_TO_PROJECT);
        }
        return this;
    }

    TeamRequirementsValidator requireNoTeam(Team team) {
        if (isNotEmpty(team)) {
            addError(TEAM_EXISTS);
        }
        return this;
    }

    TeamRequirementsValidator requireName(String name) {
        if (isEmpty(name)) {
            addError(EMPTY_TEAM_NAME);
        }
        return this;
    }

    TeamRequirementsValidator requireValidCurrentlyImplementedProjects(int currentlyImplementedProjects) {
        if (currentlyImplementedProjects < 0) {
            addError(INVALID_NUMBER_OF_CURRENTLY_IMPLEMENTED_PROJECT_BY_TEAM);
        }
        return this;
    }

    TeamRequirementsValidator requireValidMembers(List<Employee> members) {
        neverNull(members).forEach(this::requireValidMember);
        return this;
    }

    TeamRequirementsValidator requireValidMember(Employee member) {
        if (isEmpty(member)) {
            addError(EMPTY_TEAM_MEMBER);
        } else if (member.hasNoFirstName()) {
            addError(EMPTY_TEAM_MEMBER_FIRST_NAME);
        } else if (member.hasNoLastName()) {
            addError(EMPTY_TEAM_MEMBER_LAST_NAME);
        } else if (member.hasNoJobPosition()) {
            addError(EMPTY_TEAM_MEMBER_JOB_POSITION);
        }
        return this;
    }
}
