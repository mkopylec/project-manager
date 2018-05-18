package com.github.mkopylec.projectmanager.project.dto;

import com.github.mkopylec.projectmanager.domain.project.Project;
import com.github.mkopylec.projectmanager.domain.values.Feature;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

public class ProjectDtoMapper {

    public static List<Feature> mapNewToFeatures(List<NewFeature> newFeatures) {
        return emptyIfNull(newFeatures).stream()
                .map(ProjectDtoMapper::mapToFeature)
                .collect(toList());
    }

    public static List<ExistingProjectDraft> mapToExistingProjectDrafts(List<Project> projects) {
        return emptyIfNull(projects).stream()
                .map(ProjectDtoMapper::mapToExistingProjectDraft)
                .collect(toList());
    }

    public static ExistingProject mapToExistingProject(Project project) {
        ExistingProject existingProject = new ExistingProject();
        existingProject.setIdentifier(project.getIdentifier());
        existingProject.setName(project.getName());
        existingProject.setStatus(project.getStatus());
        existingProject.setTeam(project.getAssignedTeam());
        existingProject.setFeatures(project.getFeatures().stream()
                .map(ProjectDtoMapper::mapToProjectFeature)
                .collect(toList())
        );
        return existingProject;
    }

    public static List<Feature> mapToFeatures(List<ProjectFeature> projectFeatures) {
        return emptyIfNull(projectFeatures).stream()
                .map(ProjectDtoMapper::mapToFeature)
                .collect(toList());
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

    private static Feature mapToFeature(ProjectFeature projectFeature) {
        if (projectFeature == null) {
            return null;
        }
        return new Feature(projectFeature.getName(), projectFeature.getStatus(), projectFeature.getRequirement());
    }

    private ProjectDtoMapper() {
    }
}
