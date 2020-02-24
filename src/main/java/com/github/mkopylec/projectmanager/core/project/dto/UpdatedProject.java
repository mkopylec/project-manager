package com.github.mkopylec.projectmanager.core.project.dto;

import java.util.List;

public class UpdatedProject {

    private String identifier;
    private String name;
    private String team;
    private List<UpdatedProjectFeature> features;

    public UpdatedProject(String identifier, String name, String team, List<UpdatedProjectFeature> features) {
        this.identifier = identifier;
        this.name = name;
        this.team = team;
        this.features = features;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public List<UpdatedProjectFeature> getFeatures() {
        return features;
    }

    private UpdatedProject() {
    }
}
