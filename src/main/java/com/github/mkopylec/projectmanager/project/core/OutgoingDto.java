package com.github.mkopylec.projectmanager.project.core;

import com.github.mkopylec.projectmanager.common.core.UseCaseViolation.CommonViolationCode;
import com.github.mkopylec.projectmanager.project.core.ProjectRepository.ConcurrentProjectModification;

import java.util.List;
import java.util.UUID;

import static com.github.mkopylec.projectmanager.project.core.CompletionStatus.Done;
import static com.github.mkopylec.projectmanager.project.core.CompletionStatus.InProgress;
import static com.github.mkopylec.projectmanager.project.core.CompletionStatus.InvalidCompletionStatus;
import static com.github.mkopylec.projectmanager.project.core.CompletionStatus.ToDo;
import static com.github.mkopylec.projectmanager.project.core.FeatureName.InvalidFeatureName;
import static com.github.mkopylec.projectmanager.project.core.FeatureRequirement.InvalidFeatureRequirement;
import static com.github.mkopylec.projectmanager.project.core.FeatureRequirement.Necessary;
import static com.github.mkopylec.projectmanager.project.core.FeatureRequirement.Optional;
import static com.github.mkopylec.projectmanager.project.core.FeatureRequirement.Recommended;
import static com.github.mkopylec.projectmanager.project.core.OutgoingDto.CompletionStatus.completionStatus;
import static com.github.mkopylec.projectmanager.project.core.OutgoingDto.FeatureRequirement.featureRequirement;
import static com.github.mkopylec.projectmanager.project.core.Project.InvalidProjectFeaturesState;
import static com.github.mkopylec.projectmanager.project.core.Project.ProjectAlreadyStarted;
import static com.github.mkopylec.projectmanager.project.core.Project.ProjectNotStarted;
import static com.github.mkopylec.projectmanager.project.core.ProjectId.InvalidProjectId;
import static com.github.mkopylec.projectmanager.project.core.ProjectName.InvalidProjectName;
import static com.github.mkopylec.projectmanager.project.core.ProjectRepository.NoProjectExists;
import static com.github.mkopylec.projectmanager.project.core.TeamAssignedToProject.NoTeamAssignedToProject;

public class OutgoingDto {

    public static class ExistingProjectDraft {

        private final UUID id;
        private final String name;

        ExistingProjectDraft(Project project) {
            this.id = project.getId().getValue();
            this.name = project.getName().getValue();
        }

        public UUID getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public static class ExistingProject {

        private final UUID id;
        private final String name;
        private final CompletionStatus status;
        private final String team;
        private final List<ExistingFeature> features;

        ExistingProject(Project project) {
            id = project.getId().getValue();
            name = project.getName().getValue();
            status = completionStatus(project.getStatus());
            team = project.getAssignedTeam().getValue();
            features = project.getFeatures().stream().map(ExistingFeature::new).toList();
        }

        public UUID getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public CompletionStatus getStatus() {
            return status;
        }

        public String getTeam() {
            return team;
        }

        public List<ExistingFeature> getFeatures() {
            return features;
        }
    }

    public static class ExistingFeature {

        private final String name;
        private final CompletionStatus status;
        private final FeatureRequirement requirement;

        ExistingFeature(Feature feature) {
            name = feature.getName().getValue();
            status = completionStatus(feature.getStatus());
            requirement = featureRequirement(feature.getRequirement());
        }

        public String getName() {
            return name;
        }

        public CompletionStatus getStatus() {
            return status;
        }

        public FeatureRequirement getRequirement() {
            return requirement;
        }
    }

    public enum CompletionStatus {

        TO_DO,
        IN_PROGRESS,
        DONE;

        static CompletionStatus completionStatus(com.github.mkopylec.projectmanager.project.core.CompletionStatus status) {
            return switch (status) {
                case ToDo toDo -> TO_DO;
                case InProgress inProgress -> IN_PROGRESS;
                case Done done -> DONE;
            };
        }
    }

    public enum FeatureRequirement {

        OPTIONAL,
        RECOMMENDED,
        NECESSARY;

        static FeatureRequirement featureRequirement(com.github.mkopylec.projectmanager.project.core.FeatureRequirement requirement) {
            return switch (requirement) {
                case Optional optional -> OPTIONAL;
                case Recommended recommended -> RECOMMENDED;
                case Necessary necessary -> NECESSARY;
            };
        }
    }

    public enum ViolationCode implements CommonViolationCode {

        CONCURRENT_PROJECT_MODIFICATION,
        INVALID_COMPLETION_STATUS,
        INVALID_FEATURE_NAME,
        INVALID_FEATURE_REQUIREMENT,
        INVALID_PROJECT_ID,
        INVALID_PROJECT_NAME,
        INVALID_PROJECT_FEATURES_STATE,
        NO_PROJECT_EXISTS,
        NO_TEAM_ASSIGNED_TO_PROJECT,
        PROJECT_ALREADY_STARTED,
        PROJECT_NOT_STARTED;

        static ViolationCode violationCode(ProjectBusinessRuleViolation violation) {
            return switch (violation) {
                case InvalidFeatureName invalidFeatureName -> INVALID_FEATURE_NAME;
                case InvalidProjectFeaturesState invalidProjectFeaturesState -> INVALID_PROJECT_FEATURES_STATE;
                case NoTeamAssignedToProject noTeamAssignedToProject -> NO_TEAM_ASSIGNED_TO_PROJECT;
                case ProjectAlreadyStarted projectAlreadyStarted -> PROJECT_ALREADY_STARTED;
                case ProjectNotStarted projectNotStarted -> PROJECT_NOT_STARTED;
                case InvalidProjectName invalidProjectName -> INVALID_PROJECT_NAME;
                case NoProjectExists noProjectExists -> NO_PROJECT_EXISTS;
                case InvalidProjectId invalidProjectId -> INVALID_PROJECT_ID;
                case InvalidCompletionStatus invalidCompletionStatus -> INVALID_COMPLETION_STATUS;
                case InvalidFeatureRequirement invalidFeatureRequirement -> INVALID_FEATURE_REQUIREMENT;
                case ConcurrentProjectModification concurrentProjectModification -> CONCURRENT_PROJECT_MODIFICATION;
            };
        }
    }
}
