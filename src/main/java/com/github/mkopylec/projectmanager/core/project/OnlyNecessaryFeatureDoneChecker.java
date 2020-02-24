package com.github.mkopylec.projectmanager.core.project;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.project.ProjectRequirementsValidator.projectRequirements;

class OnlyNecessaryFeatureDoneChecker extends FeatureChecker {

    @Override
    void checkFeatures(List<Feature> features) {
        projectRequirements()
                .requireNecessaryFeaturesDone(features)
                .validate();
    }
}
