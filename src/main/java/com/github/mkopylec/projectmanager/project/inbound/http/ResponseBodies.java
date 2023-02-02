package com.github.mkopylec.projectmanager.project.inbound.http;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.mkopylec.projectmanager.common.inbound.http.FailureResponseBody;
import com.github.mkopylec.projectmanager.common.inbound.http.FailureResponseBody.FailureCodeResponseBody;
import com.github.mkopylec.projectmanager.project.core.OutgoingDto.ExistingProjectDraft;
import com.github.mkopylec.projectmanager.project.core.ProjectUseCaseViolation;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.github.mkopylec.projectmanager.project.core.OutgoingDto.CompletionStatus;
import static com.github.mkopylec.projectmanager.project.core.OutgoingDto.ExistingFeature;
import static com.github.mkopylec.projectmanager.project.core.OutgoingDto.ExistingProject;
import static com.github.mkopylec.projectmanager.project.core.OutgoingDto.FeatureRequirement;
import static com.github.mkopylec.projectmanager.project.core.OutgoingDto.ViolationCode;
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.CompletionStatusBody.completionStatusBody;
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.FailureCodeBody.failureCodeBody;
import static com.github.mkopylec.projectmanager.project.inbound.http.ResponseBodies.FeatureRequirementBody.featureRequirementBody;

public class ResponseBodies {

    public record ExistingProjectDraftBody(UUID id, String name) {

        ExistingProjectDraftBody(ExistingProjectDraft projectDraft) {
            this(projectDraft.getId(), projectDraft.getName());
        }
    }

    public record ExistingProjectDraftsBody(List<ExistingProjectDraftBody> projectDrafts) {

    }

    public record ExistingProjectBody(UUID id, String name, CompletionStatusBody status, String team, List<ExistingFeatureBody> features) {

        ExistingProjectBody(ExistingProject project) {
            this(project.getId(), project.getName(), completionStatusBody(project.getStatus()), project.getTeam(), project.getFeatures().stream().map(ExistingFeatureBody::new).toList());
        }
    }

    public record ExistingFeatureBody(String name, CompletionStatusBody status, FeatureRequirementBody requirement) {

        ExistingFeatureBody(ExistingFeature feature) {
            this(feature.getName(), completionStatusBody(feature.getStatus()), featureRequirementBody(feature.getRequirement()));
        }
    }

    public enum CompletionStatusBody {

        TO_DO,
        IN_PROGRESS,
        DONE;

        static CompletionStatusBody completionStatusBody(CompletionStatus status) {
            return switch (status) {
                case TO_DO -> TO_DO;
                case IN_PROGRESS -> IN_PROGRESS;
                case DONE -> DONE;
            };
        }
    }

    public enum FeatureRequirementBody {

        OPTIONAL,
        RECOMMENDED,
        NECESSARY;

        static FeatureRequirementBody featureRequirementBody(FeatureRequirement requirement) {
            return switch (requirement) {
                case OPTIONAL -> OPTIONAL;
                case RECOMMENDED -> RECOMMENDED;
                case NECESSARY -> NECESSARY;
            };
        }
    }

    public static class FailureBody extends FailureResponseBody<FailureCodeBody> {

        @JsonCreator
        private FailureBody(@JsonProperty("code") FailureCodeBody code, @JsonProperty("properties") Map<String, Object> properties) {
            super(code, properties);
        }

        FailureBody(FailureCodeBody code) {
            super(code);
        }

        FailureBody(ProjectUseCaseViolation violation) {
            super(failureCodeBody(violation.getCode()), violation.getProperties());
        }
    }

    public enum FailureCodeBody implements FailureCodeResponseBody {

        CONCURRENT_PROJECT_MODIFICATION,
        INVALID_COMPLETION_STATUS,
        INVALID_FEATURE_NAME,
        INVALID_FEATURE_REQUIREMENT,
        INVALID_PROJECT_ID,
        INVALID_PROJECT_NAME,
        INVALID_PROJECT_FEATURES_STATE,
        NO_PROJECT_EXISTS,
        NO_PROJECT_NAME,
        NO_PROJECT_STATUS,
        NO_TEAM_ASSIGNED_TO_PROJECT,
        PROJECT_ALREADY_STARTED,
        PROJECT_NOT_STARTED,
        UNEXPECTED_ERROR;

        static FailureCodeBody failureCodeBody(ViolationCode code) {
            return switch (code) {
                case CONCURRENT_PROJECT_MODIFICATION -> CONCURRENT_PROJECT_MODIFICATION;
                case INVALID_COMPLETION_STATUS -> INVALID_COMPLETION_STATUS;
                case INVALID_FEATURE_NAME -> INVALID_FEATURE_NAME;
                case INVALID_FEATURE_REQUIREMENT -> INVALID_FEATURE_REQUIREMENT;
                case INVALID_PROJECT_ID -> INVALID_PROJECT_ID;
                case INVALID_PROJECT_NAME -> INVALID_PROJECT_NAME;
                case INVALID_PROJECT_FEATURES_STATE -> INVALID_PROJECT_FEATURES_STATE;
                case NO_PROJECT_EXISTS -> NO_PROJECT_EXISTS;
                case NO_TEAM_ASSIGNED_TO_PROJECT -> NO_TEAM_ASSIGNED_TO_PROJECT;
                case PROJECT_ALREADY_STARTED -> PROJECT_ALREADY_STARTED;
                case PROJECT_NOT_STARTED -> PROJECT_NOT_STARTED;
            };
        }
    }
}
