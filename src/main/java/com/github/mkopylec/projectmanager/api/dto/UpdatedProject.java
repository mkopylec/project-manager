package com.github.mkopylec.projectmanager.api.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class UpdatedProject {

    @NotBlank(message = "EMPTY_UPDATED_PROJECT_NAME")
    private String name;
    private String team;
    private List<@NotNull(message = "EMPTY_UPDATED_PROJECT_FEATURE") @Valid UpdatedProjectFeature> features;

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
