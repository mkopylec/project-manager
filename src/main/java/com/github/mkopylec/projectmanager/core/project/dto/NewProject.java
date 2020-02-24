package com.github.mkopylec.projectmanager.core.project.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("features", features)
                .toString();
    }

    private NewProject() {
    }
}
