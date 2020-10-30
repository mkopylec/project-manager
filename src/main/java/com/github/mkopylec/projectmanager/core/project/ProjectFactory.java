package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.api.dto.NewFeature;
import com.github.mkopylec.projectmanager.api.dto.NewProject;
import com.github.mkopylec.projectmanager.api.dto.NewProjectDraft;
import com.github.mkopylec.projectmanager.api.dto.UpdatedProjectFeature;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.utils.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.utils.Utilities.mapElements;

@Component
class ProjectFactory {

    Project createProjectDraft(NewProjectDraft newProjectDraft) {
        return new Project(newProjectDraft.getName());
    }

    Project createFullProject(NewProject newProject) {
        var features = mapElements(newProject.getFeatures(), this::createFeature);
        return new Project(newProject.getName(), features);
    }

    List<Feature> createFeatures(List<UpdatedProjectFeature> projectFeatures) {
        return mapElements(projectFeatures, this::createFeature);
    }

    private Feature createFeature(NewFeature newFeature) {
        if (isEmpty(newFeature)) {
            return null;
        }
        var requirement = Requirement.from(newFeature.getRequirement());
        return new Feature(newFeature.getName(), requirement);
    }

    private Feature createFeature(UpdatedProjectFeature projectFeature) {
        if (isEmpty(projectFeature)) {
            return null;
        }
        var status = Status.from(projectFeature.getStatus());
        var requirement = Requirement.from(projectFeature.getRequirement());
        return new Feature(projectFeature.getName(), status, requirement);
    }
}
