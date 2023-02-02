package com.github.mkopylec.projectmanager.project.inbound.http;

import java.util.List;
import java.util.UUID;

import static com.github.mkopylec.projectmanager.common.support.ListUtils.mapToUnmodifiable;
import static com.github.mkopylec.projectmanager.project.core.IncomingDto.CompletionStatus;
import static com.github.mkopylec.projectmanager.project.core.IncomingDto.FeatureRequirement;
import static com.github.mkopylec.projectmanager.project.core.IncomingDto.NewFeature;
import static com.github.mkopylec.projectmanager.project.core.IncomingDto.NewProject;
import static com.github.mkopylec.projectmanager.project.core.IncomingDto.NewProjectDraft;
import static com.github.mkopylec.projectmanager.project.core.IncomingDto.ProjectEnding;
import static com.github.mkopylec.projectmanager.project.core.IncomingDto.ProjectEndingCondition;
import static com.github.mkopylec.projectmanager.project.core.IncomingDto.UpdatedFeature;
import static com.github.mkopylec.projectmanager.project.core.IncomingDto.UpdatedProject;

public class RequestBodies {

    public record NewProjectDraftBody(String name) {

        NewProjectDraft toNewProjectDraft() {
            return new NewProjectDraft(name);
        }
    }

    public record NewProjectBody(String name, List<NewFeatureBody> features) {

        NewProject toNewProject() {
            return new NewProject(name, mapToUnmodifiable(features, NewFeatureBody::toNewFeature));
        }
    }

    public record UpdatedProjectBody(String name, String team, List<UpdatedFeatureBody> features) {

        UpdatedProject toUpdatedProject(UUID projectId) {
            return new UpdatedProject(projectId, name, team, mapToUnmodifiable(features, UpdatedFeatureBody::toUpdatedFeature));
        }
    }

    public record NewFeatureBody(String name, FeatureRequirementBody requirement) {

        NewFeature toNewFeature() {
            return new NewFeature(name, requirement.toIncomingFeatureRequirement());
        }
    }

    public record UpdatedFeatureBody(String name, CompletionStatusBody status, FeatureRequirementBody requirement) {

        UpdatedFeature toUpdatedFeature() {
            return new UpdatedFeature(name, status.toIncomingCompletionStatus(), requirement.toIncomingFeatureRequirement());
        }
    }

    public record ProjectEndingBody(ProjectEndingConditionBody condition) {

        ProjectEnding toProjectEnding(UUID projectId) {
            return new ProjectEnding(projectId, condition.toProjectEndingCondition());
        }
    }

    public enum CompletionStatusBody {

        TO_DO,
        IN_PROGRESS,
        DONE;

        CompletionStatus toIncomingCompletionStatus() {
            return switch (this) {
                case TO_DO -> CompletionStatus.TO_DO;
                case IN_PROGRESS -> CompletionStatus.IN_PROGRESS;
                case DONE -> CompletionStatus.DONE;
            };
        }
    }

    public enum FeatureRequirementBody {

        OPTIONAL,
        RECOMMENDED,
        NECESSARY;

        FeatureRequirement toIncomingFeatureRequirement() {
            return switch (this) {
                case OPTIONAL -> FeatureRequirement.OPTIONAL;
                case RECOMMENDED -> FeatureRequirement.RECOMMENDED;
                case NECESSARY -> FeatureRequirement.NECESSARY;
            };
        }
    }

    public enum ProjectEndingConditionBody {

        ONLY_NECESSARY_FEATURES_DONE,
        ALL_FEATURES_DONE;

        ProjectEndingCondition toProjectEndingCondition() {
            return switch (this) {
                case ONLY_NECESSARY_FEATURES_DONE -> ProjectEndingCondition.ONLY_NECESSARY_FEATURES_DONE;
                case ALL_FEATURES_DONE -> ProjectEndingCondition.ALL_FEATURES_DONE;
            };
        }
    }
}
