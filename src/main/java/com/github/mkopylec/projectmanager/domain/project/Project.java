package com.github.mkopylec.projectmanager.domain.project;

import com.github.mkopylec.projectmanager.domain.team.Team;
import com.github.mkopylec.projectmanager.domain.values.Feature;
import com.github.mkopylec.projectmanager.domain.values.Status;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.annotation.Id;

import java.util.List;

import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_FEATURE;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_FEATURE_NAME;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_FEATURE_REQUIREMENT;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_FEATURE_STATUS;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_PROJECT_IDENTIFIER;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_PROJECT_NAME;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.PROJECT_ALREADY_ENDED;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.PROJECT_ALREADY_STARTED;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.UNASSIGNED_TEAM;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.UNSTARTED_PROJECT;
import static com.github.mkopylec.projectmanager.domain.exceptions.PreCondition.when;
import static com.github.mkopylec.projectmanager.domain.values.Status.DONE;
import static com.github.mkopylec.projectmanager.domain.values.Status.IN_PROGRESS;
import static com.github.mkopylec.projectmanager.domain.values.Status.TO_DO;
import static java.util.Collections.unmodifiableList;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Project {

    @Id
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
        features = normalize(features);
        validateFeatures(features, "Error creating '" + name + "' project");
        this.identifier = identifier;
        this.name = name;
        this.status = TO_DO;
        this.features = features;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public void rename(String name) {
        validateName(name, "Error renaming '" + identifier + "' project");
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public String getAssignedTeam() {
        return assignedTeam;
    }

    public void assignTeam(Team team) {
        assignedTeam = team == null ? null : team.getName();
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void updateFeatures(List<Feature> features) {
        features = normalize(features);
        validateFeatures(features, "Error updating '" + identifier + "' project features");
        this.features = features;
    }

    public void start() {
        String message = "Error starting '" + identifier + "' project";
        requireAssignedTeam(message);
        requireUnstarted(message);
        status = IN_PROGRESS;
    }

    public void end(FeatureChecker featureChecker, ApplicationEventPublisher publisher) {
        String message = "Error starting '" + identifier + "' project";
        requireStarted(message);
        featureChecker.checkFeatures(features, message);
        status = DONE;
        EndedProject endedProject = new EndedProject(identifier);
        publisher.publishEvent(endedProject);
    }

    private List<Feature> normalize(List<Feature> features) {
        return unmodifiableList(emptyIfNull(features));
    }

    private void validateName(String name, String message) {
        when(isBlank(name))
                .thenInvalidEntity(EMPTY_PROJECT_NAME, message);
    }

    private void validateFeatures(List<Feature> features, String message) {
        features.forEach(feature -> validateFeature(feature, message));
    }

    private void validateFeature(Feature feature, String message) {
        when(feature == null)
                .thenInvalidEntity(EMPTY_FEATURE, message);
        when(feature.isUnnamed())
                .thenInvalidEntity(EMPTY_FEATURE_NAME, message);
        when(feature.hasNoStatus())
                .thenInvalidEntity(EMPTY_FEATURE_STATUS, message);
        when(feature.hasNoRequirement())
                .thenInvalidEntity(EMPTY_FEATURE_REQUIREMENT, message);
    }

    private void validateIdentifier(String identifier, String message) {
        when(isBlank(identifier))
                .thenInvalidEntity(EMPTY_PROJECT_IDENTIFIER, message);
    }

    private void requireAssignedTeam(String message) {
        when(isBlank(assignedTeam))
                .thenInvalidEntity(UNASSIGNED_TEAM, message);
    }

    private void requireUnstarted(String message) {
        when(status.isAtLeastStarted())
                .thenInvalidEntity(PROJECT_ALREADY_STARTED, message);
    }

    private void requireStarted(String message) {
        when(status.isNotStarted())
                .thenInvalidEntity(UNSTARTED_PROJECT, message);
        when(status.isDone())
                .thenInvalidEntity(PROJECT_ALREADY_ENDED, message);
    }

    private Project() {
    }
}
