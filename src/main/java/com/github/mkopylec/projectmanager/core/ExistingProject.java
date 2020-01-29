package com.github.mkopylec.projectmanager.core;

import java.util.List;

public class ExistingProject {

    private String identifier;
    private String name;
    private Status status;
    private String team;
    private List<ExistingProjectFeature> features;

    ExistingProject() {
    }

    public String getIdentifier() {
        return identifier;
    }

    ExistingProject setIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public String getName() {
        return name;
    }

    ExistingProject setName(String name) {
        this.name = name;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    ExistingProject setStatus(Status status) {
        this.status = status;
        return this;
    }

    public String getTeam() {
        return team;
    }

    ExistingProject setTeam(String team) {
        this.team = team;
        return this;
    }

    public List<ExistingProjectFeature> getFeatures() {
        return features;
    }

    ExistingProject setFeatures(List<ExistingProjectFeature> features) {
        this.features = features;
        return this;
    }
}
