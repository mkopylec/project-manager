package com.github.mkopylec.projectmanager.api.dto;

public class ProjectEndingCondition {

    private boolean onlyNecessaryFeatureDone;

    public ProjectEndingCondition(boolean onlyNecessaryFeatureDone) {
        this.onlyNecessaryFeatureDone = onlyNecessaryFeatureDone;
    }

    public boolean isOnlyNecessaryFeatureDone() {
        return onlyNecessaryFeatureDone;
    }

    private ProjectEndingCondition() {
    }
}
