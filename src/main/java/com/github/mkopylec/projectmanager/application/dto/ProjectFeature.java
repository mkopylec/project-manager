package com.github.mkopylec.projectmanager.application.dto;

import com.github.mkopylec.projectmanager.domain.values.Status;

public class ProjectFeature {

    private String name;
    private Status status;
    private String requirement;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }
}
