package com.github.mkopylec.projectmanager.core.project;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.project.ErrorCode.PROJECT_ENDING_CONDITION_NOT_FULFILLED;
import static com.github.mkopylec.projectmanager.core.project.PreCondition.when;

class OnlyNecessaryFeatureDoneChecker implements FeatureChecker {

    @Override
    public void checkFeatures(List<Feature> features, String errorMessage) {
        features.forEach(feature -> when(feature.isNecessaryAndUndone())
                .thenInvalidProject(PROJECT_ENDING_CONDITION_NOT_FULFILLED, errorMessage)
        );
    }
}
