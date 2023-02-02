package com.github.mkopylec.projectmanager.project.core;

import com.github.mkopylec.projectmanager.project.core.CompletionStatus.ToDo;

import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation.requireNoBusinessRuleViolation;
import static java.util.Objects.requireNonNull;

public class Feature {

    private FeatureName name;
    private CompletionStatus status;
    private FeatureRequirement requirement;

    Feature(FeatureName name, CompletionStatus status, FeatureRequirement requirement) {
        setName(name);
        setStatus(status);
        setRequirement(requirement);
    }

    Feature(FeatureName name, FeatureRequirement requirement) {
        this(name, new ToDo(), requirement);
    }

    public static Feature fromPersistentState(FeatureName name, CompletionStatus status, FeatureRequirement requirement) {
        return requireNoBusinessRuleViolation(() -> new Feature(name, status, requirement));
    }

    public FeatureName getName() {
        return name;
    }

    public CompletionStatus getStatus() {
        return status;
    }

    public FeatureRequirement getRequirement() {
        return requirement;
    }

    private void setName(FeatureName name) {
        this.name = requireNonNull(name, "No feature name");
    }

    private void setStatus(CompletionStatus status) {
        this.status = requireNonNull(status, "No feature status");
    }

    private void setRequirement(FeatureRequirement requirement) {
        this.requirement = requireNonNull(requirement, "No feature requirement");
    }
}
