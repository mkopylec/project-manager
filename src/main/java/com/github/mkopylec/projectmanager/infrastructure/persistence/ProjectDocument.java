package com.github.mkopylec.projectmanager.infrastructure.persistence;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "projects")
class ProjectDocument {

    @Id
    private String identifier;
    private String name;
    private String status;
    private String assignedTeam;
    private List<FeatureDocument> features;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedTeam() {
        return assignedTeam;
    }

    public void setAssignedTeam(String assignedTeam) {
        this.assignedTeam = assignedTeam;
    }

    public List<FeatureDocument> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureDocument> features) {
        this.features = features;
    }
}
