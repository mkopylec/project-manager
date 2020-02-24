package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.common.Validator;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.neverNull;
import static com.github.mkopylec.projectmanager.core.team.TeamErrorCode.EMPTY_TEAM_MEMBER;
import static com.github.mkopylec.projectmanager.core.team.TeamErrorCode.EMPTY_TEAM_MEMBER_FIRST_NAME;
import static com.github.mkopylec.projectmanager.core.team.TeamErrorCode.EMPTY_TEAM_MEMBER_JOB_POSITION;
import static com.github.mkopylec.projectmanager.core.team.TeamErrorCode.EMPTY_TEAM_MEMBER_LAST_NAME;
import static com.github.mkopylec.projectmanager.core.team.TeamErrorCode.EMPTY_TEAM_NAME;
import static com.github.mkopylec.projectmanager.core.team.TeamErrorCode.INVALID_NUMBER_OF_CURRENTLY_IMPLEMENTED_PROJECT_BY_TEAM;
import static com.github.mkopylec.projectmanager.core.team.TeamErrorCode.MISSING_TEAM;
import static com.github.mkopylec.projectmanager.core.team.TeamErrorCode.TEAM_EXISTS;

class TeamRequirementsValidator extends Validator<TeamErrorCode> {

    static TeamRequirementsValidator teamRequirements() {
        return new TeamRequirementsValidator();
    }

    private TeamRequirementsValidator() {
        super(TeamException::new);
    }

    TeamRequirementsValidator requireTeam(Team team) {
        return require(team, MISSING_TEAM);
    }

    TeamRequirementsValidator requireNoTeam(Team team) {
        return requireNo(team, TEAM_EXISTS);
    }

    TeamRequirementsValidator requireName(String name) {
        return require(name, EMPTY_TEAM_NAME);
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
