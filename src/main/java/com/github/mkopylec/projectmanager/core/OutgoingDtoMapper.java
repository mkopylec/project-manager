package com.github.mkopylec.projectmanager.core;

import com.github.mkopylec.projectmanager.api.dto.ExistingProject;
import com.github.mkopylec.projectmanager.api.dto.ExistingProjectDraft;
import com.github.mkopylec.projectmanager.api.dto.ExistingProjectFeature;
import com.github.mkopylec.projectmanager.api.dto.ExistingTeam;
import com.github.mkopylec.projectmanager.api.dto.ExistingTeamMember;
import com.github.mkopylec.projectmanager.core.project.Feature;
import com.github.mkopylec.projectmanager.core.project.Project;
import com.github.mkopylec.projectmanager.core.team.Member;
import com.github.mkopylec.projectmanager.core.team.Team;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.Utilities.mapElements;

@Component
class OutgoingDtoMapper {

    List<ExistingProjectDraft> mapToExistingProjectDrafts(List<Project> projects) {
        return mapElements(projects, this::mapToExistingProjectDraft);
    }

    ExistingProject mapToExistingProject(Project project) {
        return new ExistingProject()
                .setIdentifier(project.getIdentifier())
                .setName(project.getName())
                .setStatus(project.getStatus().name())
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

    private ExistingTeamMember mapToExistingTeamMember(Member member) {
        return new ExistingTeamMember()
                .setFirstName(member.getFirstName())
                .setLastName(member.getLastName())
                .setJobPosition(member.getJobPosition().name());
    }

    private ExistingProjectDraft mapToExistingProjectDraft(Project project) {
        return new ExistingProjectDraft()
                .setIdentifier(project.getIdentifier())
                .setName(project.getName());
    }

    private ExistingProjectFeature mapToExistingProjectFeature(Feature feature) {
        return new ExistingProjectFeature()
                .setName(feature.getName())
                .setStatus(feature.getStatus().name())
                .setRequirement(feature.getRequirement().name());
    }
}
