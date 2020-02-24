package com.github.mkopylec.projectmanager.core.project.dto;

import java.util.List;

public class ExistingProject {

    private String identifier;
    private String name;
    private Status status;
    private String team;
    private List<ExistingProjectFeature> features;

    public ExistingProject(String identifier, String name, Status status, String team, List<ExistingProjectFeature> features) {
        this.identifier = identifier;
        this.name = name;
        this.status = status;
        this.team = team;
        this.features = features;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public String getTeam() {
        return team;
    }

    public List<ExistingProjectFeature> getFeatures() {
        return features;
    }

    private ExistingProject() {
    }
}
