package com.github.mkopylec.projectmanager.project.outbound.mongodb;

import com.github.mkopylec.projectmanager.common.core.AggregateStateVersion;
import com.github.mkopylec.projectmanager.project.core.CompletionStatus;
import com.github.mkopylec.projectmanager.project.core.Feature;
import com.github.mkopylec.projectmanager.project.core.FeatureName;
import com.github.mkopylec.projectmanager.project.core.FeatureRequirement;
import com.github.mkopylec.projectmanager.project.core.Project;
import com.github.mkopylec.projectmanager.project.core.ProjectId;
import com.github.mkopylec.projectmanager.project.core.ProjectName;
import com.github.mkopylec.projectmanager.project.core.TeamAssignedToProject;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.UUID;

import static com.github.mkopylec.projectmanager.common.support.ListUtils.mapToUnmodifiable;

@Document("projects")
class ProjectDocument {

    @MongoId
    private final UUID id;
    @Version
    private Integer version;
    private final String name;
    private final String status;
    private final String assignedTeam;
    private final List<FeatureDocument> features;

    @PersistenceCreator
    private ProjectDocument(UUID id, Integer version, String name, String status, String assignedTeam, List<FeatureDocument> features) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.status = status;
        this.assignedTeam = assignedTeam;
        this.features = features;
    }

    ProjectDocument(Project project) {
        this(
                project.getId().getValue(),
                project.getStateVersion().getValue(),
                project.getName().getValue(),
                project.getStatus().getValue(),
                project.getAssignedTeam().getValue(),
                mapToUnmodifiable(project.getFeatures(), FeatureDocument::new)
        );
    }

    Project toProject() {
        return Project.fromPersistentState(
                ProjectId.fromPersistentState(id),
                AggregateStateVersion.fromPersistentState(version),
                ProjectName.fromPersistentState(name),
                CompletionStatus.fromPersistentState(status),
                TeamAssignedToProject.fromPersistentState(assignedTeam),
                mapToUnmodifiable(features, FeatureDocument::toFeature)
        );
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    private static class FeatureDocument {

        private final String name;
        private final String status;
        private final String requirement;

        @PersistenceCreator
        private FeatureDocument(String name, String status, String requirement) {
            this.name = name;
            this.status = status;
            this.requirement = requirement;
        }

        private FeatureDocument(Feature feature) {
            this(feature.getName().getValue(), feature.getStatus().getValue(), feature.getRequirement().getValue());
        }

        private Feature toFeature() {
            return Feature.fromPersistentState(
                    FeatureName.fromPersistentState(name),
                    CompletionStatus.fromPersistentState(status),
                    FeatureRequirement.fromPersistentState(requirement)
            );
        }
    }
}
