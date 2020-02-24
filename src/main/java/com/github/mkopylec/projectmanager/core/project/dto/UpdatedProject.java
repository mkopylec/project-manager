package com.github.mkopylec.projectmanager.core.project.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("identifier", identifier)
                .append("name", name)
                .append("team", team)
                .append("features", features)
                .toString();
    }

    private UpdatedProject() {
    }
}
