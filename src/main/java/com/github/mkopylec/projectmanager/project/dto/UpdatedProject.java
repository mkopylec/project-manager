package com.github.mkopylec.projectmanager.project.dto;

import java.util.List;

public class UpdatedProject {

    private String name;
    private String team;
    private List<ProjectFeature> features;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public List<ProjectFeature> getFeatures() {
        return features;
    }

    public void setFeatures(List<ProjectFeature> features) {
        this.features = features;
    }
}
