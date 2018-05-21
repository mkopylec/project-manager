package com.github.mkopylec.projectmanager.domain.values;

import static com.github.mkopylec.projectmanager.domain.values.Requirement.INVALID;
import static com.github.mkopylec.projectmanager.domain.values.Status.TO_DO;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Feature {

    private String name;
    private Status status;
    private Requirement requirement;

    public Feature(String name, Requirement requirement) {
        this.name = name;
        this.requirement = requirement;
        status = TO_DO;
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

    public Requirement getRequirement() {
        return requirement;
    }

    public boolean hasNoRequirement() {
        return !hasRequirement();
    }

    public boolean hasInvalidRequirement() {
        return hasRequirement() && requirement == INVALID;
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
