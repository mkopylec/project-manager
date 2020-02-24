package com.github.mkopylec.projectmanager.core.project.dto;

import java.util.List;

public class NewProject {

    private String name;
    private List<NewFeature> features;

    public NewProject(String name, List<NewFeature> features) {
        this.name = name;
        this.features = features;
    }

    public String getName() {
        return name;
    }

    public List<NewFeature> getFeatures() {
        return features;
    }

    private NewProject() {
    }
}
