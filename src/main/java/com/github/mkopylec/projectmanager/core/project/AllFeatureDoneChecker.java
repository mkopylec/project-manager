package com.github.mkopylec.projectmanager.core.project;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.project.ErrorCode.PROJECT_ENDING_CONDITION_NOT_FULFILLED;
import static com.github.mkopylec.projectmanager.core.project.PreCondition.when;

class AllFeatureDoneChecker implements FeatureChecker {

    @Override
    public void checkFeatures(List<Feature> features, String errorMessage) {
        features.forEach(feature -> when(feature.isUndone())
                .thenInvalidProject(PROJECT_ENDING_CONDITION_NOT_FULFILLED, errorMessage)
        );
    }
}
