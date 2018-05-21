package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.team.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.core.team.dto.TeamMember;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.InvalidStateSupportingEnumMapper.mapTo;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

class DtoMapper {

    static Employee mapToEmployee(TeamMember teamMember) {
        JobPosition jobPosition = mapTo(JobPosition.class, teamMember.getJobPosition());
        return new Employee(teamMember.getFirstName(), teamMember.getLastName(), jobPosition);
    }

    static List<ExistingTeam> mapToExistingTeams(List<Team> teams) {
        return emptyIfNull(teams).stream()
                .map(DtoMapper::mapToExistingTeam)
                .collect(toList());
    }

    private static ExistingTeam mapToExistingTeam(Team team) {
        ExistingTeam existingTeam = new ExistingTeam();
        existingTeam.setName(team.getName());
        existingTeam.setBusy(team.isBusy());
        existingTeam.setCurrentlyImplementedProjects(team.getCurrentlyImplementedProjects());
        existingTeam.setMembers(team.getMembers().stream()
                .map(DtoMapper::mapToTeamMember)
                .collect(toList())
        );
        return existingTeam;
    }

    private static TeamMember mapToTeamMember(Employee employee) {
        TeamMember member = new TeamMember();
        member.setFirstName(employee.getFirstName());
        member.setLastName(employee.getLastName());
        member.setJobPosition(employee.getJobPosition().name());
        return member;
    }

    private DtoMapper() {
    }
}
