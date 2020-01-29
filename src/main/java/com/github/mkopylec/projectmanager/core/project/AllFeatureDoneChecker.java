package com.github.mkopylec.projectmanager.core.project;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.project.ProjectRequirementsValidator.requirements;

class AllFeatureDoneChecker extends FeatureChecker {

    @Override
    void checkFeatures(List<Feature> features, String errorMessage) {
        requirements()
                .requireAllFeaturesDone(features, errorMessage)
                .validate();
    }
}
