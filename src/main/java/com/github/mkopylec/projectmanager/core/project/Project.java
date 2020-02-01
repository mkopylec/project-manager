package com.github.mkopylec.projectmanager.core.project;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.Utilities.allEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.neverNull;
import static com.github.mkopylec.projectmanager.core.project.ProjectRequirementsValidator.requirements;
import static com.github.mkopylec.projectmanager.core.project.Status.DONE;
import static com.github.mkopylec.projectmanager.core.project.Status.IN_PROGRESS;
import static com.github.mkopylec.projectmanager.core.project.Status.TO_DO;
import static java.util.Collections.unmodifiableList;

public class Project {

    private String identifier;
    private String name;
    private Status status;
    private String assignedTeam;
    private List<Feature> features;

    static Project project(String identifier, String name) {
        return new Project(identifier, name, TO_DO, null, null);
    }

    static Project project(String identifier, String name, List<Feature> features) {
        return new Project(identifier, name, TO_DO, null, features);
    }

    private Project(String identifier, String name, Status status, String assignedTeam, List<Feature> features) {
        requirements()
                .requireIdentifier(identifier)
                .requireName(name)
                .requireValidStatus(status)
                .requireValidFeatures(features)
                .validate();
        this.identifier = identifier;
        this.name = name;
        this.status = status;
        this.assignedTeam = assignedTeam;
        this.features = neverNull(features);
    }

    public String getIdentifier() {
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
        requirements()
                .requireName(name)
                .validate();
        this.name = name;
    }

    void assignTeam(String teamName) {
        assignedTeam = teamName;
    }

    void updateFeatures(List<Feature> features) {
        requirements()
                .requireValidFeatures(features)
                .validate();
        this.features = neverNull(features);
    }

    void start() {
        requirements()
                .requireAssignedTeam(assignedTeam)
                .requireToDoStatus(status)
                .validate();
        status = IN_PROGRESS;
    }

    EndedProject end(FeatureChecker featureChecker) {
        requirements()
                .requireInProgressStatus(status)
                .validate();
        featureChecker.checkFeatures(features);
        status = DONE;
        return new EndedProject(identifier);
    }

    public static class ProjectPersistenceFactory {

        public Project createProject(String identifier, String name, Status status, String assignedTeam, List<Feature> features) {
            try {
                return allEmpty(identifier, name, status, assignedTeam, features) ? null : new Project(identifier, name, status, assignedTeam, features);
            } catch (Exception e) {
                throw new IllegalStateException("Error creating project from persistent state", e);
            }
        }
    }
}
