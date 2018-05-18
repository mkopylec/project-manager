package com.github.mkopylec.projectmanager.project.dto;

import java.util.List;

public class NewProject {

    private String name;
    private List<NewFeature> features;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NewFeature> getFeatures() {
        return features;
    }

    public void setFeatures(List<NewFeature> features) {
        this.features = features;
    }
}
