package com.github.mkopylec.projectmanager.core.project;

import static com.github.mkopylec.projectmanager.core.project.Status.TO_DO;
import static org.apache.commons.lang3.StringUtils.isBlank;

class Feature {

    private String name;
    private Status status;
    private Requirement requirement;

    Feature(String name, Requirement requirement) {
        this(name, TO_DO, requirement);
    }

    Feature(String name, Status status, Requirement requirement) {
        this.name = name;
        this.status = status;
        this.requirement = requirement;
    }

    String getName() {
        return name;
    }

    boolean isUnnamed() {
        return isBlank(name);
    }

    Status getStatus() {
        return status;
    }

    boolean hasNoStatus() {
        return !hasStatus();
    }

    boolean hasInvalidStatus() {
        return hasStatus() && status.isInvalid();
    }

    boolean isUndone() {
        return hasStatus() && !status.isDone();
    }

    boolean isNecessaryAndUndone() {
        return hasStatus() && !status.isDone() && hasRequirement() && requirement.isNecessary();
    }

    Requirement getRequirement() {
        return requirement;
    }

    boolean hasNoRequirement() {
        return !hasRequirement();
    }

    boolean hasInvalidRequirement() {
        return hasRequirement() && requirement.isInvalid();
    }

    private boolean hasStatus() {
        return status != null;
    }

    private boolean hasRequirement() {
        return requirement != null;
    }

    private Feature() {
    }
}
