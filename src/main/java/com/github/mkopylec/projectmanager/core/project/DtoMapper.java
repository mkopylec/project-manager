package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.common.TeamAssignedToProject;
import com.github.mkopylec.projectmanager.core.project.dto.ExistingProject;
import com.github.mkopylec.projectmanager.core.project.dto.ExistingProjectDraft;
import com.github.mkopylec.projectmanager.core.project.dto.NewFeature;
import com.github.mkopylec.projectmanager.core.project.dto.ProjectFeature;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.InvalidStateSupportingEnumMapper.mapTo;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

class DtoMapper {

    static List<Feature> mapNewToFeatures(List<NewFeature> newFeatures) {
        return emptyIfNull(newFeatures).stream()
                .map(DtoMapper::mapToFeature)
                .collect(toList());
    }

    static List<ExistingProjectDraft> mapToExistingProjectDrafts(List<Project> projects) {
        return emptyIfNull(projects).stream()
                .map(DtoMapper::mapToExistingProjectDraft)
                .collect(toList());
    }

    static ExistingProject mapToExistingProject(Project project) {
        ExistingProject existingProject = new ExistingProject();
        existingProject.setIdentifier(project.getIdentifier());
        existingProject.setName(project.getName());
        existingProject.setStatus(project.getStatus().name());
        existingProject.setTeam(project.getAssignedTeam());
        existingProject.setFeatures(project.getFeatures().stream()
                .map(DtoMapper::mapToProjectFeature)
                .collect(toList())
        );
        return existingProject;
    }

    static List<Feature> mapToFeatures(List<ProjectFeature> projectFeatures) {
        return emptyIfNull(projectFeatures).stream()
                .map(DtoMapper::mapToFeature)
                .collect(toList());
    }

    static TeamAssignedToProject mapToTeamAssignedToProject(Project project) {
        String assignedTeam = project.getAssignedTeam();
        return assignedTeam == null ? null : new TeamAssignedToProject(assignedTeam);
    }

    private static Feature mapToFeature(NewFeature newFeature) {
        if (newFeature == null) {
            return null;
        }
        Requirement requirement = mapTo(Requirement.class, newFeature.getRequirement());
        return new Feature(newFeature.getName(), requirement);
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
        projectFeature.setStatus(feature.getStatus().name());
        projectFeature.setRequirement(feature.getRequirement().name());
        return projectFeature;
    }

    private static Feature mapToFeature(ProjectFeature projectFeature) {
        if (projectFeature == null) {
            return null;
        }
        Status status = mapTo(Status.class, projectFeature.getStatus());
        Requirement requirement = mapTo(Requirement.class, projectFeature.getRequirement());
        return new Feature(projectFeature.getName(), status, requirement);
    }

    private DtoMapper() {
    }
}
