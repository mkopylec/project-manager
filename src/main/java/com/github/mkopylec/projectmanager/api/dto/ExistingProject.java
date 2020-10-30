package com.github.mkopylec.projectmanager.api.dto;

import java.util.List;
import java.util.UUID;

public class ExistingProject {

    private UUID identifier;
    private String name;
    private String status;
    private String team;
    private List<ExistingProjectFeature> features;

    public UUID getIdentifier() {
        return identifier;
    }

    public ExistingProject setIdentifier(UUID identifier) {
        this.identifier = identifier;
        return this;
    }

    public String getName() {
        return name;
    }

    public ExistingProject setName(String name) {
        this.name = name;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ExistingProject setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getTeam() {
        return team;
    }

    public ExistingProject setTeam(String team) {
        this.team = team;
        return this;
    }

    public List<ExistingProjectFeature> getFeatures() {
        return features;
    }

    public ExistingProject setFeatures(List<ExistingProjectFeature> features) {
        this.features = features;
        return this;
    }
}
