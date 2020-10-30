package com.github.mkopylec.projectmanager.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class NewFeature {

    @NotBlank(message = "EMPTY_NEW_FEATURE_NAME")
    private String name;
    @NotBlank(message = "EMPTY_NEW_FEATURE_REQUIREMENT")
    @Pattern(message = "INVALID_NEW_FEATURE_REQUIREMENT", regexp = "^(\\s*|OPTIONAL|RECOMMENDED|NECESSARY)$")
    private String requirement;

    public NewFeature(String name, String requirement) {
        this.name = name;
        this.requirement = requirement;
    }

    public String getName() {
        return name;
    }

    public String getRequirement() {
        return requirement;
    }

    private NewFeature() {
    }
}
