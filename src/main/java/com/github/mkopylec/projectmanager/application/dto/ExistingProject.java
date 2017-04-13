package com.github.mkopylec.projectmanager.application.dto;

import java.util.List;

public class ExistingProject {

    private String identifier;
    private String name;
    private String status;
    private List<ProjectFeature> features;

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

    public List<ProjectFeature> getFeatures() {
        return features;
    }

    public void setFeatures(List<ProjectFeature> features) {
        this.features = features;
    }
}
