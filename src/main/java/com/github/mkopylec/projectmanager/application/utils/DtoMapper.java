package com.github.mkopylec.projectmanager.application.utils;

import com.github.mkopylec.projectmanager.application.dto.ExistingProjectDraft;
import com.github.mkopylec.projectmanager.application.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.application.dto.NewFeature;
import com.github.mkopylec.projectmanager.application.dto.TeamMember;
import com.github.mkopylec.projectmanager.domain.project.Project;
import com.github.mkopylec.projectmanager.domain.team.Team;
import com.github.mkopylec.projectmanager.domain.values.Employee;
import com.github.mkopylec.projectmanager.domain.values.Feature;
import com.github.mkopylec.projectmanager.domain.values.JobPosition;
import com.github.mkopylec.projectmanager.domain.values.Requirement;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class DtoMapper {

    public static Employee mapToEmployee(TeamMember teamMember) {
        JobPosition jobPosition = mapToJobPosition(teamMember.getJobPosition());
        return new Employee(teamMember.getFirstName(), teamMember.getLastName(), jobPosition);
    }

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

    private static Feature mapToFeature(NewFeature newFeature) {
        if (newFeature == null) {
            return null;
        }
        Requirement requirement = mapToRequirement(newFeature.getRequirement());
        return new Feature(newFeature.getName(), requirement);
    }

    private static JobPosition mapToJobPosition(String jobPosition) {
        if (isBlank(jobPosition)) {
            return null;
        }
        try {
            return JobPosition.valueOf(jobPosition);
        } catch (IllegalArgumentException e) {
            return JobPosition.INVALID;
        }
    }

    private static Requirement mapToRequirement(String requirement) {
        if (isBlank(requirement)) {
            return null;
        }
        try {
            return Requirement.valueOf(requirement);
        } catch (IllegalArgumentException e) {
            return Requirement.INVALID;
        }
    }

    private static ExistingProjectDraft mapToExistingProjectDraft(Project project) {
        ExistingProjectDraft existingProjectDraft = new ExistingProjectDraft();
        existingProjectDraft.setIdentifier(project.getIdentifier());
        existingProjectDraft.setName(project.getName());
        return existingProjectDraft;
    }

    private DtoMapper() {
    }
}
