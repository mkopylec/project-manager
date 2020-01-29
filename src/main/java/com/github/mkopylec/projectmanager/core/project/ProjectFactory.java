package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.NewFeature;
import com.github.mkopylec.projectmanager.core.NewProject;
import com.github.mkopylec.projectmanager.core.NewProjectDraft;
import com.github.mkopylec.projectmanager.core.UpdatedProjectFeature;

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
        String identifier = identifierGenerator.generateUniqueIdentifier();
        return project(identifier, newProjectDraft.getName());
    }

    Project createFullProject(NewProject newProject) {
        String identifier = identifierGenerator.generateUniqueIdentifier();
        List<Feature> features = mapElements(newProject.getFeatures(), this::createFeature);
        return project(identifier, newProject.getName(), features);
    }

    List<Feature> createFeatures(List<UpdatedProjectFeature> projectFeatures) {
        return mapElements(projectFeatures, this::createFeature);
    }

    private Feature createFeature(NewFeature newFeature) {
        if (isEmpty(newFeature)) {
            return null;
        }
        Requirement requirement = convertEnum(newFeature.getRequirement(), Requirement.class);
        return feature(newFeature.getName(), requirement);
    }

    private Feature createFeature(UpdatedProjectFeature projectFeature) {
        if (isEmpty(projectFeature)) {
            return null;
        }
        Status status = convertEnum(projectFeature.getStatus(), Status.class);
        Requirement requirement = convertEnum(projectFeature.getRequirement(), Requirement.class);
        return feature(projectFeature.getName(), status, requirement);
    }
}
