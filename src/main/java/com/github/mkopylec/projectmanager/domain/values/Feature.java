package com.github.mkopylec.projectmanager.domain.values;

import static com.github.mkopylec.projectmanager.domain.values.Requirement.createRequirement;
import static com.github.mkopylec.projectmanager.domain.values.Status.TO_DO;
import static com.github.mkopylec.projectmanager.domain.values.Status.createStatus;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Feature {

    private String name;
    private Status status;
    private Requirement requirement;

    public Feature(String name, String requirement) {
        this(name, requirement, TO_DO.name());
    }

    public Feature(String name, String requirement, String status) {
        this.name = name;
        this.requirement = createRequirement(requirement);
        this.status = createStatus(status);
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

    public String getStatusName() {
        return hasNoStatus() ? null : status.name();
    }

    public boolean hasNoStatus() {
        return status == null;
    }

    public boolean hasInvalidStatus() {
        return status == Status._INVALID;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public String getRequirementName() {
        return hasNoRequirement() ? null : requirement.name();
    }

    public boolean hasNoRequirement() {
        return requirement == null;
    }

    public boolean hasInvalidRequirement() {
        return requirement == Requirement._INVALID;
    }

    private Feature() {
    }
}
