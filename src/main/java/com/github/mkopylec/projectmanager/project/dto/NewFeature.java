package com.github.mkopylec.projectmanager.project.dto;

import com.github.mkopylec.projectmanager.domain.values.Requirement;

public class NewFeature {

    private String name;
    private Requirement requirement;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }
}
