package com.github.mkopylec.projectmanager.application.utils;

import java.util.List;

import com.github.mkopylec.projectmanager.application.dto.ExistingProject;
import com.github.mkopylec.projectmanager.application.dto.ExistingProjectDraft;
import com.github.mkopylec.projectmanager.application.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.application.dto.NewFeature;
import com.github.mkopylec.projectmanager.application.dto.ProjectFeature;
import com.github.mkopylec.projectmanager.application.dto.TeamMember;
import com.github.mkopylec.projectmanager.domain.project.Project;
import com.github.mkopylec.projectmanager.domain.team.Team;
import com.github.mkopylec.projectmanager.domain.values.Employee;
import com.github.mkopylec.projectmanager.domain.values.Feature;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

public class DtoMapper {

    public static List<ExistingTeam> mapToExistingTeams(List<Team> teams) {
        return emptyIfNull(teams).stream()
                .map(DtoMapper::mapToExistingTeam)
                .collect(toList());
    }

    public static List<Feature> mapToFeatures(List<NewFeature> newFeatures) {
        return emptyIfNull(newFeatures).stream()
                .map(DtoMapper::mapToFeature)
                .collect(toList());
    }

    public static List<ExistingProjectDraft> mapToExistingProjectDrafts(List<Project> projects) {
        return emptyIfNull(projects).stream()
                .map(DtoMapper::mapToExistingProjectDraft)
                .collect(toList());
    }

    public static ExistingProject mapToExistingProject(Project project) {
        ExistingProject existingProject = new ExistingProject();
        existingProject.setIdentifier(project.getIdentifier());
        existingProject.setName(project.getName());
        existingProject.setStatus(project.getStatus());
        existingProject.setTeam(project.getAssignedTeam());
        existingProject.setFeatures(project.getFeatures().stream()
                .map(DtoMapper::mapToProjectFeature)
                .collect(toList())
        );
        return existingProject;
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
        member.setJobPosition(employee.getJobPosition());
        return member;
    }

    private static Feature mapToFeature(NewFeature newFeature) {
        if (newFeature == null) {
            return null;
        }
        return new Feature(newFeature.getName(), newFeature.getRequirement());
    }

    private static ExistingProjectDraft mapToExistingProjectDraft(Project project) {
        ExistingProjectDraft existingProjectDraft = new ExistingProjectDraft();
        existingProjectDraft.setIdentifier(project.getIdentifier());
        existingProjectDraft.setName(project.getName());
        return existingProjectDraft;
    }

    private static ProjectFeature mapToProjectFeature(Feature feature) {
        ProjectFeature projectFeature = new ProjectFeature();
        projectFeature.setName(feature.getName());
        projectFeature.setStatus(feature.getStatus());
        projectFeature.setRequirement(feature.getRequirement());
        return projectFeature;
    }

    private DtoMapper() {
    }
}
