package com.github.mkopylec.projectmanager.core.project;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.project.ProjectRequirementsValidator.projectRequirements;

class AllFeatureDoneChecker extends FeatureChecker {

    @Override
    void checkFeatures(List<Feature> features) {
        projectRequirements()
                .requireAllFeaturesDone(features)
                .validate();
    }
}
