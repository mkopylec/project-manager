package com.github.mkopylec.projectmanager.api.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class NewProject {

    @NotBlank(message = "EMPTY_NEW_PROJECT_NAME")
    private String name;
    private List<@NotNull(message = "EMPTY_NEW_PROJECT_FEATURE") @Valid NewFeature> features;

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
