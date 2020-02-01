package com.github.mkopylec.projectmanager.core.project;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.project.ProjectRequirementsValidator.requirements;

class OnlyNecessaryFeatureDoneChecker extends FeatureChecker {

    @Override
    void checkFeatures(List<Feature> features) {
        requirements()
                .requireNecessaryFeaturesDone(features)
                .validate();
    }
}
