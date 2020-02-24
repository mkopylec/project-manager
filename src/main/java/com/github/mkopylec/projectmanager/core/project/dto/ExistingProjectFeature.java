package com.github.mkopylec.projectmanager.core.project.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class ExistingProjectFeature {

    private String name;
    private Status status;
    private Requirement requirement;

    public ExistingProjectFeature(String name, Status status, Requirement requirement) {
        this.name = name;
        this.status = status;
        this.requirement = requirement;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("status", status)
                .append("requirement", requirement)
                .toString();
    }

    private ExistingProjectFeature() {
    }
}
