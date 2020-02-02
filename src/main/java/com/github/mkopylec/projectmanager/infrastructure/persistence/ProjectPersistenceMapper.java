package com.github.mkopylec.projectmanager.infrastructure.persistence;

import com.github.mkopylec.projectmanager.core.project.Feature;
import com.github.mkopylec.projectmanager.core.project.Feature.FeaturePersistenceFactory;
import com.github.mkopylec.projectmanager.core.project.Project;
import com.github.mkopylec.projectmanager.core.project.Project.ProjectPersistenceFactory;
import com.github.mkopylec.projectmanager.core.project.Requirement;
import com.github.mkopylec.projectmanager.core.project.Status;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.mapElements;
import static com.github.mkopylec.projectmanager.core.common.Utilities.toEnum;

class ProjectPersistenceMapper {

    private ProjectPersistenceFactory projectFactory = new ProjectPersistenceFactory();
    private FeaturePersistenceFactory featureFactory = new FeaturePersistenceFactory();

    ProjectDocument map(Project project) {
        return new ProjectDocument()
                .setIdentifier(project.getIdentifier())
                .setName(project.getName())
                .setStatus(project.getStatus().name())
                .setAssignedTeam(project.getAssignedTeam())
                .setFeatures(mapElements(project.getFeatures(), this::map));
    }

    Project map(ProjectDocument document) {
        if (isEmpty(document)) {
            return null;
        }
        var status = toEnum(document.getStatus(), Status.class);
        var features = mapElements(document.getFeatures(), this::map);
        return projectFactory.createProject(document.getIdentifier(), document.getName(), status, document.getAssignedTeam(), features);
    }

    private FeatureDocument map(Feature feature) {
        return new FeatureDocument()
                .setName(feature.getName())
                .setStatus(feature.getStatus().name())
                .setRequirement(feature.getRequirement().name());
    }

    private Feature map(FeatureDocument document) {
        if (isEmpty(document)) {
            return null;
        }
        var status = toEnum(document.getStatus(), Status.class);
        var requirement = toEnum(document.getRequirement(), Requirement.class);
        return featureFactory.createFeature(document.getName(), status, requirement);
    }
}
