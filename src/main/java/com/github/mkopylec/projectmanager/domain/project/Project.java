package com.github.mkopylec.projectmanager.domain.project;

import java.util.List;

import com.github.mkopylec.projectmanager.domain.values.Feature;
import com.github.mkopylec.projectmanager.domain.values.Status;

import org.springframework.data.annotation.Id;

import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_FEATURE;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_FEATURE_NAME;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_FEATURE_REQUIREMENT;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_FEATURE_STATUS;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_PROJECT_IDENTIFIER;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.EMPTY_PROJECT_NAME;
import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.INVALID_FEATURE_REQUIREMENT;
import static com.github.mkopylec.projectmanager.domain.exceptions.PreCondition.when;
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
        validateIdentifier(identifier, "Error creating '" + name + "'project");
        validateName(name, "Error creating '" + identifier + "'project");
        features = normalize(features);
        validateFeatures(name, features);
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

    public String getStatusName() {
        return status.name();
    }

    public String getAssignedTeam() {
        return assignedTeam;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    private List<Feature> normalize(List<Feature> features) {
        return unmodifiableList(emptyIfNull(features));
    }

    private void validateFeatures(String name, List<Feature> features) {
        features.forEach(feature -> validateFeature(feature, "Error creating '" + name + "'project"));
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
        when(feature.hasInvalidRequirement())
                .thenInvalidEntity(INVALID_FEATURE_REQUIREMENT, message);
    }

    private void validateIdentifier(String identifier, String message) {
        when(isBlank(identifier))
                .thenInvalidEntity(EMPTY_PROJECT_IDENTIFIER, message);
    }

    private void validateName(String name, String message) {
        when(isBlank(name))
                .thenInvalidEntity(EMPTY_PROJECT_NAME, message);
    }

    private Project() {
    }
}
