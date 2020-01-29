package com.github.mkopylec.projectmanager.core.project;

import java.util.List;

abstract class FeatureChecker {

    abstract void checkFeatures(List<Feature> features, String errorMessage);

    static FeatureChecker featureChecker(boolean onlyNecessaryFeatureDone) {
        return onlyNecessaryFeatureDone
                ? new OnlyNecessaryFeatureDoneChecker()
                : new AllFeatureDoneChecker();
    }
}
