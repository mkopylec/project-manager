package com.github.mkopylec.projectmanager.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "projects")
class ProjectDocument {

    @Id
    private String identifier;
    private String name;
    private String status;
    private String assignedTeam;
    private List<FeatureDocument> features;

    String getIdentifier() {
        return identifier;
    }

    ProjectDocument setIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    String getName() {
        return name;
    }

    ProjectDocument setName(String name) {
        this.name = name;
        return this;
    }

    String getStatus() {
        return status;
    }

    ProjectDocument setStatus(String status) {
        this.status = status;
        return this;
    }

    String getAssignedTeam() {
        return assignedTeam;
    }

    ProjectDocument setAssignedTeam(String assignedTeam) {
        this.assignedTeam = assignedTeam;
        return this;
    }

    List<FeatureDocument> getFeatures() {
        return features;
    }

    ProjectDocument setFeatures(List<FeatureDocument> features) {
        this.features = features;
        return this;
    }
}
