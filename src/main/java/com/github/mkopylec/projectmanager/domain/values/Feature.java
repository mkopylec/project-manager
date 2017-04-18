package com.github.mkopylec.projectmanager.domain.values;

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
        return status == null;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public boolean hasNoRequirement() {
        return requirement == null;
    }

    private Feature() {
    }
}
