package com.github.mkopylec.projectmanager.core.project.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("identifier", identifier)
                .append("name", name)
                .append("status", status)
                .append("team", team)
                .append("features", features)
                .toString();
    }

    private ExistingProject() {
    }
}
