package com.github.mkopylec.projectmanager.project.dto;

import com.github.mkopylec.projectmanager.domain.values.Requirement;
import com.github.mkopylec.projectmanager.domain.values.Status;

public class ProjectFeature {

    private String name;
    private Status status;
    private Requirement requirement;

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

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }
}
