package com.github.mkopylec.projectmanager.core.project;

import org.springframework.data.annotation.PersistenceConstructor;

import static com.github.mkopylec.projectmanager.api.exception.InvalidEntityException.require;
import static com.github.mkopylec.projectmanager.core.project.ProjectViolation.EMPTY_FEATURE_NAME;
import static com.github.mkopylec.projectmanager.core.project.ProjectViolation.EMPTY_FEATURE_REQUIREMENT;
import static com.github.mkopylec.projectmanager.core.project.ProjectViolation.EMPTY_FEATURE_STATUS;
import static com.github.mkopylec.projectmanager.core.project.Requirement.NECESSARY;
import static com.github.mkopylec.projectmanager.core.project.Status.DONE;
import static com.github.mkopylec.projectmanager.core.project.Status.TO_DO;
import static com.github.mkopylec.projectmanager.core.utils.Utilities.isNotEmpty;

public class Feature {

    private String name;
    private Status status;
    private Requirement requirement;

    Feature(String name, Requirement requirement) {
        this(name, TO_DO, requirement);
    }

    @PersistenceConstructor
    Feature(String name, Status status, Requirement requirement) {
        require(isNotEmpty(name), EMPTY_FEATURE_NAME);
        require(isNotEmpty(status), EMPTY_FEATURE_STATUS);
        require(isNotEmpty(requirement), EMPTY_FEATURE_REQUIREMENT);
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

    boolean isDone() {
        return status == DONE;
    }

    boolean isNecessaryAndUndone() {
        return requirement == NECESSARY && status != DONE;
    }
}
