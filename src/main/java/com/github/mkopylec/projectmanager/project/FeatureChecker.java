package com.github.mkopylec.projectmanager.project;

import com.github.mkopylec.projectmanager.domain.values.Feature;

import java.util.List;

public interface FeatureChecker {

    void checkFeatures(List<Feature> features, String errorMessage);

    static FeatureChecker resolveFeatureChecker(boolean onlyNecessaryFeatureDone) {
        return onlyNecessaryFeatureDone
                ? new OnlyNecessaryFeatureDoneChecker()
                : new AllFeatureDoneChecker();
    }
}
