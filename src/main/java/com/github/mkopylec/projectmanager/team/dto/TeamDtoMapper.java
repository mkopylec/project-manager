package com.github.mkopylec.projectmanager.team.dto;

import com.github.mkopylec.projectmanager.domain.team.Team;
import com.github.mkopylec.projectmanager.domain.values.Employee;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

public class TeamDtoMapper {

    public static Employee mapToEmployee(TeamMember teamMember) {
        return new Employee(teamMember.getFirstName(), teamMember.getLastName(), teamMember.getJobPosition());
    }

    public static List<ExistingTeam> mapToExistingTeams(List<Team> teams) {
        return emptyIfNull(teams).stream()
                .map(TeamDtoMapper::mapToExistingTeam)
                .collect(toList());
    }

    private static ExistingTeam mapToExistingTeam(Team team) {
        ExistingTeam existingTeam = new ExistingTeam();
        existingTeam.setName(team.getName());
        existingTeam.setBusy(team.isBusy());
        existingTeam.setCurrentlyImplementedProjects(team.getCurrentlyImplementedProjects());
        existingTeam.setMembers(team.getMembers().stream()
                .map(TeamDtoMapper::mapToTeamMember)
                .collect(toList())
        );
        return existingTeam;
    }

    private static TeamMember mapToTeamMember(Employee employee) {
        TeamMember member = new TeamMember();
        member.setFirstName(employee.getFirstName());
        member.setLastName(employee.getLastName());
        member.setJobPosition(employee.getJobPosition());
        return member;
    }

    private TeamDtoMapper() {
    }
}
