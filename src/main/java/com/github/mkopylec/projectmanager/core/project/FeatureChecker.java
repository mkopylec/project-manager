package com.github.mkopylec.projectmanager.core.project;

import java.util.List;

interface FeatureChecker {

    void checkFeatures(List<Feature> features, String errorMessage);

    static FeatureChecker resolveFeatureChecker(boolean onlyNecessaryFeatureDone) {
        return onlyNecessaryFeatureDone
                ? new OnlyNecessaryFeatureDoneChecker()
                : new AllFeatureDoneChecker();
    }
}
