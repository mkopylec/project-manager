package com.github.mkopylec.projectmanager.domain.values;

import static com.github.mkopylec.projectmanager.domain.values.Status.TO_DO;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Feature {

    private String name;
    private Status status;
    private Requirement requirement;

    public Feature(String name, Requirement requirement) {
        this(name, TO_DO, requirement);
    }

    public Feature(String name, Status status, Requirement requirement) {
        this.name = name;
        this.status = status;
        this.requirement = requirement;
    }

    public String getName() {
        return name;
    }

    public boolean isUnnamed() {
        return isBlank(name);
    }

    public Status getStatus() {
        return status;
    }

    public boolean hasNoStatus() {
        return !hasStatus();
    }

    public boolean hasInvalidStatus() {
        return hasStatus() && status == Status.INVALID;
    }

    public boolean isUndone() {
        return !status.isDone();
    }

    public boolean isNecessaryAndUndone() {
        return !status.isDone() && requirement.isNecessary();
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public boolean hasNoRequirement() {
        return !hasRequirement();
    }

    public boolean hasInvalidRequirement() {
        return hasRequirement() && requirement == Requirement.INVALID;
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
