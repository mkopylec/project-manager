package com.github.mkopylec.projectmanager.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UpdatedProjectFeature {

    @NotBlank(message = "EMPTY_UPDATED_PROJECT_FEATURE_NAME")
    private String name;
    @NotBlank(message = "EMPTY_UPDATED_PROJECT_FEATURE_STATUS")
    @Pattern(message = "INVALID_UPDATED_PROJECT_FEATURE_STATUS", regexp = "^(\\s*|TO_DO|IN_PROGRESS|DONE)$")
    private String status;
    @NotBlank(message = "EMPTY_UPDATED_PROJECT_FEATURE_REQUIREMENT")
    @Pattern(message = "INVALID_UPDATED_PROJECT_FEATURE_REQUIREMENT", regexp = "^(\\s*|OPTIONAL|RECOMMENDED|NECESSARY)$")
    private String requirement;

    public UpdatedProjectFeature(String name, String status, String requirement) {
        this.name = name;
        this.status = status;
        this.requirement = requirement;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getRequirement() {
        return requirement;
    }

    private UpdatedProjectFeature() {
    }
}
