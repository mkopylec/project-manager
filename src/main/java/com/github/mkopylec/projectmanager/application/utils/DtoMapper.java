package com.github.mkopylec.projectmanager.application.utils;

import com.github.mkopylec.projectmanager.application.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.application.dto.NewFeature;
import com.github.mkopylec.projectmanager.application.dto.TeamMember;
import com.github.mkopylec.projectmanager.domain.team.Team;
import com.github.mkopylec.projectmanager.domain.values.Feature;

import static java.util.stream.Collectors.toList;

public class DtoMapper {

    public static ExistingTeam mapToExistingTeam(Team team) {
        ExistingTeam existingTeam = new ExistingTeam();
        existingTeam.setName(team.getName());
        existingTeam.setBusy(team.isBusy());
        existingTeam.setCurrentlyImplementedProjects(team.getCurrentlyImplementedProjects());
        existingTeam.setMembers(team.getMembers().stream()
                .map(employee -> {
                    TeamMember member = new TeamMember();
                    member.setFirstName(employee.getFirstName());
                    member.setLastName(employee.getLastName());
                    member.setJobPosition(employee.getJobPosition().toString());
                    return member;
                })
                .collect(toList())
        );
        return existingTeam;
    }

    public static Feature mapToFeature(NewFeature newFeature) {
        return new Feature(newFeature.getName(), newFeature.getRequirement());
    }

    private DtoMapper() {
    }
}
