package com.github.mkopylec.projectmanager.core.project;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

import static com.github.mkopylec.projectmanager.api.exception.InvalidEntityException.require;
import static com.github.mkopylec.projectmanager.core.project.ProjectViolation.EMPTY_PROJECT_FEATURE;
import static com.github.mkopylec.projectmanager.core.project.ProjectViolation.EMPTY_PROJECT_IDENTIFIER;
import static com.github.mkopylec.projectmanager.core.project.ProjectViolation.EMPTY_PROJECT_NAME;
import static com.github.mkopylec.projectmanager.core.project.ProjectViolation.EMPTY_PROJECT_STATUS;
import static com.github.mkopylec.projectmanager.core.project.ProjectViolation.EMPTY_TEAM_ASSIGNED_TO_PROJECT;
import static com.github.mkopylec.projectmanager.core.project.ProjectViolation.PROJECT_STATUS_DIFFERENT_THAN_IN_PROGRESS;
import static com.github.mkopylec.projectmanager.core.project.ProjectViolation.PROJECT_STATUS_DIFFERENT_THAN_TO_DO;
import static com.github.mkopylec.projectmanager.core.project.Status.DONE;
import static com.github.mkopylec.projectmanager.core.project.Status.IN_PROGRESS;
import static com.github.mkopylec.projectmanager.core.project.Status.TO_DO;
import static com.github.mkopylec.projectmanager.core.utils.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.utils.Utilities.isNotEmpty;
import static com.github.mkopylec.projectmanager.core.utils.Utilities.neverNull;
import static com.github.mkopylec.projectmanager.core.utils.Utilities.noneEmpty;
import static java.util.Collections.unmodifiableList;
import static java.util.UUID.randomUUID;

@Document(collection = "projects") // Use separate DTOs to persist the aggregate only if it has more than one data source
public class Project {

    @Id
    private UUID identifier;
    private String name;
    private Status status;
    private String assignedTeam;
    private List<Feature> features;

    Project(String name) {
        this(name, null);
    }

    Project(String name, List<Feature> features) {
        this(randomUUID(), name, TO_DO, null, features);
    }

    @PersistenceConstructor
    private Project(UUID identifier, String name, Status status, String assignedTeam, List<Feature> features) {
        require(isNotEmpty(identifier), EMPTY_PROJECT_IDENTIFIER);
        require(isNotEmpty(name), EMPTY_PROJECT_NAME);
        require(isNotEmpty(status), EMPTY_PROJECT_STATUS);
        require(noneEmpty(features), EMPTY_PROJECT_FEATURE);
        this.identifier = identifier;
        this.name = name;
        this.status = status;
        this.assignedTeam = assignedTeam;
        this.features = neverNull(features);
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public String getAssignedTeam() {
        return assignedTeam;
    }

    public boolean hasNoTeamAssigned() {
        return isEmpty(assignedTeam);
    }

    public List<Feature> getFeatures() {
        return unmodifiableList(features);
    }

    void rename(String name) {
        require(isNotEmpty(name), EMPTY_PROJECT_NAME);
        this.name = name;
    }

    void assignTeam(String teamName) {
        assignedTeam = teamName;
    }

    void updateFeatures(List<Feature> features) {
        require(noneEmpty(features), EMPTY_PROJECT_FEATURE);
        this.features = neverNull(features);
    }

    void start() {
        require(isNotEmpty(assignedTeam), EMPTY_TEAM_ASSIGNED_TO_PROJECT);
        require(status == TO_DO, PROJECT_STATUS_DIFFERENT_THAN_TO_DO);
        status = IN_PROGRESS;
    }

    EndedProject end(FeatureChecker featureChecker) {
        require(status == IN_PROGRESS, PROJECT_STATUS_DIFFERENT_THAN_IN_PROGRESS);
        featureChecker.checkFeatures(features);
        status = DONE;
        return new EndedProject(identifier);
    }
}
