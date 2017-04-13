package com.github.mkopylec.projectmanager.application.dto;

import java.util.List;

public class NewProject {

    private String name;
    private List<ProjectFeature> features;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProjectFeature> getFeatures() {
        return features;
    }

    public void setFeatures(List<ProjectFeature> features) {
        this.features = features;
    }
}
