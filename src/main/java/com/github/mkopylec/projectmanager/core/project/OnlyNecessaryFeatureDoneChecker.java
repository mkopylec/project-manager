package com.github.mkopylec.projectmanager.core.project;

import java.util.List;

import static com.github.mkopylec.projectmanager.api.exception.InvalidEntityException.require;
import static com.github.mkopylec.projectmanager.core.project.ProjectViolation.EMPTY_PROJECT_FEATURE;
import static com.github.mkopylec.projectmanager.core.project.ProjectViolation.UNDONE_PROJECT_NECESSARY_FEATURE;
import static com.github.mkopylec.projectmanager.core.utils.Utilities.none;
import static com.github.mkopylec.projectmanager.core.utils.Utilities.noneEmpty;

class OnlyNecessaryFeatureDoneChecker extends FeatureChecker {

    @Override
    void checkFeatures(List<Feature> features) {
        require(noneEmpty(features), EMPTY_PROJECT_FEATURE);
        require(none(Feature::isNecessaryAndUndone, features), UNDONE_PROJECT_NECESSARY_FEATURE);
    }
}
