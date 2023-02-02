package com.github.mkopylec.projectmanager.project.core;

import com.github.mkopylec.projectmanager.project.core.CompletionStatus.Done;
import com.github.mkopylec.projectmanager.project.core.FeatureRequirement.Necessary;

import java.util.List;

sealed interface ProjectEndingFeaturesPolicy {

    boolean isEndingAllowed(List<Feature> features);

    final class OnlyNecessaryFeaturesDone implements ProjectEndingFeaturesPolicy {

        @Override
        public boolean isEndingAllowed(List<Feature> features) {
            return features.stream().noneMatch(feature -> !(feature.getStatus() instanceof Done) && feature.getRequirement() instanceof Necessary);
        }
    }

    final class AllFeaturesDone implements ProjectEndingFeaturesPolicy {

        @Override
        public boolean isEndingAllowed(List<Feature> features) {
            return features.stream().allMatch(feature -> feature.getStatus() instanceof Done);
        }
    }
}
