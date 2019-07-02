package com.github.mkopylec.projectmanager.core.project;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.project.ErrorCode.EMPTY_FEATURE;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.EMPTY_FEATURE_NAME;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.EMPTY_FEATURE_REQUIREMENT;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.EMPTY_FEATURE_STATUS;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.EMPTY_PROJECT_IDENTIFIER;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.EMPTY_PROJECT_NAME;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.INVALID_FEATURE_REQUIREMENT;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.INVALID_FEATURE_STATUS;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.PROJECT_ALREADY_ENDED;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.PROJECT_ALREADY_STARTED;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.UNASSIGNED_TEAM;
import static com.github.mkopylec.projectmanager.core.project.ErrorCode.UNSTARTED_PROJECT;
import static com.github.mkopylec.projectmanager.core.project.PreCondition.when;
import static com.github.mkopylec.projectmanager.core.project.Status.DONE;
import static com.github.mkopylec.projectmanager.core.project.Status.IN_PROGRESS;
import static com.github.mkopylec.projectmanager.core.project.Status.TO_DO;
import static java.util.Collections.unmodifiableList;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.trimToNull;

public class Project {

    private String identifier;
    private String name;
    private Status status;
    private String assignedTeam;
    private List<Feature> features;

    Project(String identifier, String name) {
        this(identifier, name, null);
    }

    Project(String identifier, String name, List<Feature> features) {
        validateIdentifier(identifier, "Error creating '" + name + "' project");
        validateName(name, "Error creating '" + identifier + "' project");
        features = emptyIfNull(features);
        validateFeatures(features, "Error creating '" + name + "' project");
        this.identifier = identifier;
        this.name = name;
        this.status = TO_DO;
        this.features = features;
    }

    String getIdentifier() {
        return identifier;
    }

    String getName() {
        return name;
    }

    void rename(String name) {
        validateName(name, "Error renaming '" + identifier + "' project");
        this.name = name;
    }

    Status getStatus() {
        return status;
    }

    String getAssignedTeam() {
        return assignedTeam;
    }

    void assignTeam(String teamName) {
        assignedTeam = trimToNull(teamName);
    }

    List<Feature> getFeatures() {
        return unmodifiableList(features);
    }

    void updateFeatures(List<Feature> features) {
        features = emptyIfNull(features);
        validateFeatures(features, "Error updating '" + identifier + "' project features");
        this.features = features;
    }

    void start() {
        String message = "Error starting '" + identifier + "' project";
        requireAssignedTeam(message);
        requireUnstarted(message);
        status = IN_PROGRESS;
    }

    EndedProject end(FeatureChecker featureChecker) {
        String message = "Error starting '" + identifier + "' project";
        requireStarted(message);
        featureChecker.checkFeatures(features, message);
        status = DONE;
        return new EndedProject(identifier);
    }

    private void validateName(String name, String message) {
        when(isBlank(name))
                .thenInvalidProject(EMPTY_PROJECT_NAME, message);
    }

    private void validateFeatures(List<Feature> features, String message) {
        features.forEach(feature -> validateFeature(feature, message));
    }

    private void validateFeature(Feature feature, String message) {
        when(feature == null)
                .thenInvalidProject(EMPTY_FEATURE, message);
        when(feature.isUnnamed())
                .thenInvalidProject(EMPTY_FEATURE_NAME, message);
        when(feature.hasNoStatus())
                .thenInvalidProject(EMPTY_FEATURE_STATUS, message);
        when(feature.hasInvalidStatus())
                .thenInvalidProject(INVALID_FEATURE_STATUS, message);
        when(feature.hasNoRequirement())
                .thenInvalidProject(EMPTY_FEATURE_REQUIREMENT, message);
        when(feature.hasInvalidRequirement())
                .thenInvalidProject(INVALID_FEATURE_REQUIREMENT, message);
    }

    private void validateIdentifier(String identifier, String message) {
        when(isBlank(identifier))
                .thenInvalidProject(EMPTY_PROJECT_IDENTIFIER, message);
    }

    private void requireAssignedTeam(String message) {
        when(isBlank(assignedTeam))
                .thenInvalidProject(UNASSIGNED_TEAM, message);
    }

    private void requireUnstarted(String message) {
        when(status.isAtLeastStarted())
                .thenInvalidProject(PROJECT_ALREADY_STARTED, message);
    }

    private void requireStarted(String message) {
        when(status.isNotStarted())
                .thenInvalidProject(UNSTARTED_PROJECT, message);
        when(status.isDone())
                .thenInvalidProject(PROJECT_ALREADY_ENDED, message);
    }

    private Project() {
    }
}
