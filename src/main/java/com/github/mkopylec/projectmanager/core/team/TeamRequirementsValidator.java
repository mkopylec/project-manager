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

    TeamRequirementsValidator requireTeam(Team team, String message) {
        if (isEmpty(team)) {
            addError(MISSING_TEAM, message);
        }
        return this;
    }

    TeamRequirementsValidator requireTeamAssignedToProject(Team team, String message) {
        if (isEmpty(team)) {
            addError(MISSING_TEAM_ASSIGNED_TO_PROJECT, message);
        }
        return this;
    }

    TeamRequirementsValidator requireNoTeam(Team team, String message) {
        if (isNotEmpty(team)) {
            addError(TEAM_EXISTS, message);
        }
        return this;
    }

    TeamRequirementsValidator requireName(String name, String message) {
        if (isEmpty(name)) {
            addError(EMPTY_TEAM_NAME, message);
        }
        return this;
    }

    TeamRequirementsValidator requireValidCurrentlyImplementedProjects(int currentlyImplementedProjects, String message) {
        if (currentlyImplementedProjects < 0) {
            addError(INVALID_NUMBER_OF_CURRENTLY_IMPLEMENTED_PROJECT_BY_TEAM, message);
        }
        return this;
    }

    TeamRequirementsValidator requireValidMembers(List<Employee> members, String message) {
        neverNull(members).forEach(employee -> requireValidMember(employee, message));
        return this;
    }

    TeamRequirementsValidator requireValidMember(Employee member, String message) {
        if (isEmpty(member)) {
            addError(EMPTY_TEAM_MEMBER, message);
        } else if (member.hasNoFirstName()) {
            addError(EMPTY_TEAM_MEMBER_FIRST_NAME, message);
        } else if (member.hasNoLastName()) {
            addError(EMPTY_TEAM_MEMBER_LAST_NAME, message);
        } else if (member.hasNoJobPosition()) {
            addError(EMPTY_TEAM_MEMBER_JOB_POSITION, message);
        }
        return this;
    }
}
