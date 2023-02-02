package com.github.mkopylec.projectmanager.project.core;

import com.github.mkopylec.projectmanager.common.core.Aggregate;
import com.github.mkopylec.projectmanager.common.core.AggregateStateVersion;
import com.github.mkopylec.projectmanager.project.core.CompletionStatus.Done;
import com.github.mkopylec.projectmanager.project.core.CompletionStatus.InProgress;
import com.github.mkopylec.projectmanager.project.core.CompletionStatus.ToDo;

import java.time.Clock;
import java.util.List;

import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation.requireNoBusinessRuleViolation;
import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolationProperties.properties;
import static com.github.mkopylec.projectmanager.common.support.ListUtils.cleanToUnmodifiable;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

public class Project extends Aggregate<ProjectId> {

    private ProjectId id;
    private ProjectName name;
    private CompletionStatus status;
    private TeamAssignedToProject assignedTeam;
    private List<Feature> features;

    private Project(ProjectId id, AggregateStateVersion stateVersion, ProjectName name, CompletionStatus status, TeamAssignedToProject assignedTeam, List<Feature> features) {
        super(id, stateVersion);
        setId(id);
        setName(name);
        setStatus(status);
        setAssignedTeam(assignedTeam);
        setFeatures(features);
    }

    Project(ProjectName name) {
        this(name, emptyList());
    }

    Project(ProjectName name, List<Feature> features) {
        this(new ProjectId(), new AggregateStateVersion(), name, new ToDo(), new TeamAssignedToProject(), features);
    }

    public static Project fromPersistentState(ProjectId id, AggregateStateVersion stateVersion, ProjectName name, CompletionStatus status, TeamAssignedToProject assignedTeam, List<Feature> features) {
        return requireNoBusinessRuleViolation(() -> new Project(id, stateVersion, name, status, assignedTeam, features));
    }

    public ProjectId getId() {
        return id;
    }

    public ProjectName getName() {
        return name;
    }

    public CompletionStatus getStatus() {
        return status;
    }

    public TeamAssignedToProject getAssignedTeam() {
        return assignedTeam;
    }

    public List<Feature> getFeatures() {
        return unmodifiableList(features);
    }

    void rename(ProjectName name) {
        setName(name);
    }

    void assignTeam(TeamAssignedToProject assignedTeam, Clock clock) {
        assignedTeam.requireNotEmpty();
        setAssignedTeam(assignedTeam);
        raiseEvent(new ProjectTeamAssigned(id, assignedTeam, clock));
    }

    void updateFeatures(List<Feature> features) {
        setFeatures(features);
    }

    void start() {
        assignedTeam.requireNotEmpty();
        requireNotStarted();
        setStatus(new InProgress());
    }

    void end(ProjectEndingFeaturesPolicy policy, Clock clock) {
        requireStarted();
        var endingAllowed = policy.isEndingAllowed(features);
        if (!endingAllowed) {
            throw new InvalidProjectFeaturesState(id, features);
        }
        setStatus(new Done());
        raiseEvent(new ProjectEnded(id, clock));
    }

    private void setId(ProjectId id) {
        this.id = requireNonNull(id, "No project ID");
    }

    private void setName(ProjectName name) {
        this.name = requireNonNull(name, "No project name");
    }

    private void setStatus(CompletionStatus status) {
        this.status = requireNonNull(status, "No project status");
    }

    private void setAssignedTeam(TeamAssignedToProject assignedTeam) {
        this.assignedTeam = requireNonNull(assignedTeam, "No team assigned to project");
    }

    private void setFeatures(List<Feature> features) {
        this.features = cleanToUnmodifiable(features);
    }

    private void requireNotStarted() {
        if (!(status instanceof ToDo)) {
            throw new ProjectAlreadyStarted(id, status);
        }
    }

    private void requireStarted() {
        if (!(status instanceof InProgress)) {
            throw new ProjectNotStarted(id, status);
        }
    }

    static final class ProjectAlreadyStarted extends ProjectBusinessRuleViolation {

        private ProjectAlreadyStarted(ProjectId id, CompletionStatus status) {
            super(properties("id", id).append("status", status));
        }
    }

    static final class ProjectNotStarted extends ProjectBusinessRuleViolation {

        private ProjectNotStarted(ProjectId id, CompletionStatus status) {
            super(properties("id", id).append("status", status));
        }
    }

    static final class InvalidProjectFeaturesState extends ProjectBusinessRuleViolation {

        private InvalidProjectFeaturesState(ProjectId id, List<Feature> features) {
            super(properties("id", id).append("features", features));
        }
    }
}
