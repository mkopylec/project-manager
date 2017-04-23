package com.github.mkopylec.projectmanager.domain.policies;

import java.util.List;

import com.github.mkopylec.projectmanager.domain.values.Feature;

public interface FeatureChecker {

    void checkFeatures(List<Feature> features, String errorMessage);

    static FeatureChecker resolveFeatureChecker(boolean onlyNecessaryFeatureDone) {
        return onlyNecessaryFeatureDone
                ? new OnlyNecessaryFeatureDoneChecker()
                : new AllFeatureDoneChecker();
    }
}
