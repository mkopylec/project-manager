package com.github.mkopylec.projectmanager.api.dto;

public class ExistingProjectFeature {

    private String name;
    private String status;
    private String requirement;

    public String getName() {
        return name;
    }

    public ExistingProjectFeature setName(String name) {
        this.name = name;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ExistingProjectFeature setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getRequirement() {
        return requirement;
    }

    public ExistingProjectFeature setRequirement(String requirement) {
        this.requirement = requirement;
        return this;
    }
}
