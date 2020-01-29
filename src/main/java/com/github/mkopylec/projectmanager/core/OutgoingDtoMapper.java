package com.github.mkopylec.projectmanager.core;

import com.github.mkopylec.projectmanager.core.project.Feature;
import com.github.mkopylec.projectmanager.core.project.Project;
import com.github.mkopylec.projectmanager.core.team.Employee;
import com.github.mkopylec.projectmanager.core.team.Team;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.Utilities.convertEnum;
import static com.github.mkopylec.projectmanager.core.common.Utilities.mapElements;

class OutgoingDtoMapper {

    List<ExistingProjectDraft> mapToExistingProjectDrafts(List<Project> projects) {
        return mapElements(projects, this::mapToExistingProjectDraft);
    }

    ExistingProject mapToExistingProject(Project project) {
        return new ExistingProject()
                .setIdentifier(project.getIdentifier())
                .setName(project.getName())
                .setStatus(convertEnum(project.getStatus(), Status.class))
                .setTeam(project.getAssignedTeam())
                .setFeatures(mapElements(project.getFeatures(), this::mapToExistingProjectFeature));
    }

    List<ExistingTeam> mapToExistingTeams(List<Team> teams) {
        return mapElements(teams, this::mapToExistingTeam);
    }

    private ExistingTeam mapToExistingTeam(Team team) {
        return new ExistingTeam()
                .setName(team.getName())
                .setBusy(team.isBusy())
                .setCurrentlyImplementedProjects(team.getCurrentlyImplementedProjects())
                .setMembers(mapElements(team.getMembers(), this::mapToExistingTeamMember));
    }

    private ExistingTeamMember mapToExistingTeamMember(Employee employee) {
        return new ExistingTeamMember()
                .setFirstName(employee.getFirstName())
                .setLastName(employee.getLastName())
                .setJobPosition(convertEnum(employee.getJobPosition(), JobPosition.class));
    }

    private ExistingProjectDraft mapToExistingProjectDraft(Project project) {
        return new ExistingProjectDraft()
                .setIdentifier(project.getIdentifier())
                .setName(project.getName());
    }

    private ExistingProjectFeature mapToExistingProjectFeature(Feature feature) {
        return new ExistingProjectFeature()
                .setName(feature.getName())
                .setStatus(convertEnum(feature.getStatus(), Status.class))
                .setRequirement(convertEnum(feature.getRequirement(), Requirement.class));
    }
}
