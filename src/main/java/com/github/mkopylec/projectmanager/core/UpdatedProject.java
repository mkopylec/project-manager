package com.github.mkopylec.projectmanager.core;

import java.util.List;

public class UpdatedProject {

    private String name;
    private String team;
    private List<UpdatedProjectFeature> features;

    public UpdatedProject(String name, String team, List<UpdatedProjectFeature> features) {
        this.name = name;
        this.team = team;
        this.features = features;
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
