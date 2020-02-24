package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.project.dto.NewFeature;
import com.github.mkopylec.projectmanager.core.project.dto.NewProject;
import com.github.mkopylec.projectmanager.core.project.dto.NewProjectDraft;
import com.github.mkopylec.projectmanager.core.project.dto.UpdatedProjectFeature;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.Utilities.convertEnum;
import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.mapElements;
import static com.github.mkopylec.projectmanager.core.project.Feature.feature;
import static com.github.mkopylec.projectmanager.core.project.Project.project;

class ProjectFactory {

    private UniqueIdentifierGenerator identifierGenerator;

    ProjectFactory(UniqueIdentifierGenerator identifierGenerator) {
        this.identifierGenerator = identifierGenerator;
    }

    Project createProjectDraft(NewProjectDraft newProjectDraft) {
        var identifier = identifierGenerator.generateUniqueIdentifier();
        return project(identifier, newProjectDraft.getName());
    }

    Project createFullProject(NewProject newProject) {
        var identifier = identifierGenerator.generateUniqueIdentifier();
        var features = mapElements(newProject.getFeatures(), this::createFeature);
        return project(identifier, newProject.getName(), features);
    }

    List<Feature> createFeatures(List<UpdatedProjectFeature> projectFeatures) {
        return mapElements(projectFeatures, this::createFeature);
    }

    private Feature createFeature(NewFeature newFeature) {
        if (isEmpty(newFeature)) {
            return null;
        }
        var requirement = convertEnum(newFeature.getRequirement(), Requirement.class);
        return feature(newFeature.getName(), requirement);
    }

    private Feature createFeature(UpdatedProjectFeature projectFeature) {
        if (isEmpty(projectFeature)) {
            return null;
        }
        var status = convertEnum(projectFeature.getStatus(), Status.class);
        var requirement = convertEnum(projectFeature.getRequirement(), Requirement.class);
        return feature(projectFeature.getName(), status, requirement);
    }
}
