package com.github.mkopylec.projectmanager.domain.policies;

import java.util.List;

import com.github.mkopylec.projectmanager.domain.values.Feature;

import static com.github.mkopylec.projectmanager.domain.exceptions.ErrorCode.PROJECT_ENDING_CONDITION_NOT_FULFILLED;
import static com.github.mkopylec.projectmanager.domain.exceptions.PreCondition.when;

class AllFeatureDoneChecker implements FeatureChecker {

    @Override
    public void checkFeatures(List<Feature> features, String errorMessage) {
        features.forEach(feature -> when(feature.isUndone())
                .thenInvalidEntity(PROJECT_ENDING_CONDITION_NOT_FULFILLED, errorMessage)
        );
    }
}
