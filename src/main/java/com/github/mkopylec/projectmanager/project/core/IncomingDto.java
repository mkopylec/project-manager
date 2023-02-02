package com.github.mkopylec.projectmanager.project.core;

import com.github.mkopylec.projectmanager.project.core.FeatureRequirement.Optional;

import java.util.List;
import java.util.UUID;

import static com.github.mkopylec.projectmanager.project.core.CompletionStatus.Done;
import static com.github.mkopylec.projectmanager.project.core.CompletionStatus.InProgress;
import static com.github.mkopylec.projectmanager.project.core.CompletionStatus.ToDo;
import static com.github.mkopylec.projectmanager.project.core.FeatureRequirement.Necessary;
import static com.github.mkopylec.projectmanager.project.core.FeatureRequirement.Recommended;
import static com.github.mkopylec.projectmanager.project.core.ProjectEndingFeaturesPolicy.AllFeaturesDone;
import static com.github.mkopylec.projectmanager.project.core.ProjectEndingFeaturesPolicy.OnlyNecessaryFeaturesDone;
import static java.util.Collections.unmodifiableList;

public class IncomingDto {

    public static class ProjectId {

        private final UUID value;

        public ProjectId(UUID value) {
            this.value = value;
        }

        com.github.mkopylec.projectmanager.project.core.ProjectId getProjectId() {
            return new com.github.mkopylec.projectmanager.project.core.ProjectId(value);
        }
    }

    public static class NewProjectDraft {

        private final String name;

        public NewProjectDraft(String name) {
            this.name = name;
        }

        ProjectName getName() {
            return new ProjectName(name);
        }
    }

    public static class NewProject {

        private final String name;
        private final List<NewFeature> features;

        public NewProject(String name, List<NewFeature> features) {
            this.name = name;
            this.features = features;
        }

        ProjectName getName() {
            return new ProjectName(name);
        }

        List<NewFeature> getFeatures() {
            return unmodifiableList(features);
        }
    }

    public static class UpdatedProject {

        private final UUID id;
        private final String name;
        private final String team;
        private final List<UpdatedFeature> features;

        public UpdatedProject(UUID id, String name, String team, List<UpdatedFeature> features) {
            this.id = id;
            this.name = name;
            this.team = team;
            this.features = features;
        }

        com.github.mkopylec.projectmanager.project.core.ProjectId getId() {
            return new com.github.mkopylec.projectmanager.project.core.ProjectId(id);
        }

        ProjectName getName() {
            return new ProjectName(name);
        }

        TeamAssignedToProject getTeam() {
            return new TeamAssignedToProject(team);
        }

        List<UpdatedFeature> getFeatures() {
            return unmodifiableList(features);
        }
    }

    public static class NewFeature {

        private final String name;
        private final FeatureRequirement requirement;

        public NewFeature(String name, FeatureRequirement requirement) {
            this.name = name;
            this.requirement = requirement;
        }

        FeatureName getName() {
            return new FeatureName(name);
        }

        com.github.mkopylec.projectmanager.project.core.FeatureRequirement getRequirement() {
            return requirement.getFeatureRequirement();
        }
    }

    public static class UpdatedFeature {

        private final String name;
        private final CompletionStatus status;
        private final FeatureRequirement requirement;

        public UpdatedFeature(String name, CompletionStatus status, FeatureRequirement requirement) {
            this.name = name;
            this.status = status;
            this.requirement = requirement;
        }

        FeatureName getName() {
            return new FeatureName(name);
        }

        com.github.mkopylec.projectmanager.project.core.CompletionStatus getStatus() {
            return status.getCompletionStatus();
        }

        com.github.mkopylec.projectmanager.project.core.FeatureRequirement getRequirement() {
            return requirement.getFeatureRequirement();
        }
    }

    public static class ProjectEnding {

        private final UUID projectId;
        private final ProjectEndingCondition condition;

        public ProjectEnding(UUID projectId, ProjectEndingCondition condition) {
            this.projectId = projectId;
            this.condition = condition;
        }

        com.github.mkopylec.projectmanager.project.core.ProjectId getProjectId() {
            return new com.github.mkopylec.projectmanager.project.core.ProjectId(projectId);
        }

        ProjectEndingFeaturesPolicy getProjectEndingFeaturesPolicy() {
            return condition.getProjectEndingFeaturesPolicy();
        }
    }

    public enum CompletionStatus {

        TO_DO,
        IN_PROGRESS,
        DONE;

        com.github.mkopylec.projectmanager.project.core.CompletionStatus getCompletionStatus() {
            return switch (this) {
                case TO_DO -> new ToDo();
                case IN_PROGRESS -> new InProgress();
                case DONE -> new Done();
            };
        }
    }

    public enum FeatureRequirement {

        OPTIONAL,
        RECOMMENDED,
        NECESSARY;

        com.github.mkopylec.projectmanager.project.core.FeatureRequirement getFeatureRequirement() {
            return switch (this) {
                case OPTIONAL -> new Optional();
                case RECOMMENDED -> new Recommended();
                case NECESSARY -> new Necessary();
            };
        }
    }

    public enum ProjectEndingCondition {

        ONLY_NECESSARY_FEATURES_DONE,
        ALL_FEATURES_DONE;

        ProjectEndingFeaturesPolicy getProjectEndingFeaturesPolicy() {
            return switch (this) {
                case ONLY_NECESSARY_FEATURES_DONE -> new OnlyNecessaryFeaturesDone();
                case ALL_FEATURES_DONE -> new AllFeaturesDone();
            };
        }
    }
}
