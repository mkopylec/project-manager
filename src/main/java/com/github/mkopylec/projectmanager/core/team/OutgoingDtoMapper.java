package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.team.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.core.team.dto.ExistingTeamMember;
import com.github.mkopylec.projectmanager.core.team.dto.JobPosition;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.Utilities.convertEnum;
import static com.github.mkopylec.projectmanager.core.common.Utilities.mapElements;

class OutgoingDtoMapper {

    List<ExistingTeam> mapToExistingTeams(List<Team> teams) {
        return mapElements(teams, this::mapToExistingTeam);
    }

    private ExistingTeam mapToExistingTeam(Team team) {
        return new ExistingTeam(
                team.getName(),
                team.getCurrentlyImplementedProjects(),
                team.isBusy(),
                mapElements(team.getMembers(), this::mapToExistingTeamMember));
    }

    private ExistingTeamMember mapToExistingTeamMember(Employee employee) {
        return new ExistingTeamMember(
                employee.getFirstName(),
                employee.getLastName(),
                convertEnum(employee.getJobPosition(), JobPosition.class));
    }
}
