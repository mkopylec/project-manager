package com.github.mkopylec.projectmanager.infrastructure.persistence;

import com.github.mkopylec.projectmanager.core.project.Feature;
import com.github.mkopylec.projectmanager.core.project.Feature.FeaturePersistenceHelper;
import com.github.mkopylec.projectmanager.core.project.Project;
import com.github.mkopylec.projectmanager.core.project.Project.ProjectPersistenceHelper;
import com.github.mkopylec.projectmanager.core.project.Requirement;
import com.github.mkopylec.projectmanager.core.project.Status;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.mapElements;
import static com.github.mkopylec.projectmanager.core.common.Utilities.toEnum;

class ProjectPersistenceMapper {

    private ProjectPersistenceHelper projectHelper = new ProjectPersistenceHelper();
    private FeaturePersistenceHelper featureHelper = new FeaturePersistenceHelper();

    ProjectDocument map(Project project) {
        return new ProjectDocument()
                .setIdentifier(projectHelper.getIdentifier(project))
                .setName(projectHelper.getName(project))
                .setStatus(projectHelper.getStatus(project).name())
                .setAssignedTeam(projectHelper.getAssignedTeam(project))
                .setFeatures(mapElements(projectHelper.getFeatures(project), this::map));
    }

    Project map(ProjectDocument document) {
        if (isEmpty(document)) {
            return null;
        }
        var status = toEnum(document.getStatus(), Status.class);
        var features = mapElements(document.getFeatures(), this::map);
        return projectHelper.createProject(document.getIdentifier(), document.getName(), status, document.getAssignedTeam(), features);
    }

    private FeatureDocument map(Feature feature) {
        return new FeatureDocument()
                .setName(featureHelper.getName(feature))
                .setStatus(featureHelper.getStatus(feature).name())
                .setRequirement(featureHelper.getRequirement(feature).name());
    }

    private Feature map(FeatureDocument document) {
        if (isEmpty(document)) {
            return null;
        }
        var status = toEnum(document.getStatus(), Status.class);
        var requirement = toEnum(document.getRequirement(), Requirement.class);
        return featureHelper.createFeature(document.getName(), status, requirement);
    }
}
