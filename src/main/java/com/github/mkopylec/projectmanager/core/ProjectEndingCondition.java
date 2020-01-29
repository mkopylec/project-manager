package com.github.mkopylec.projectmanager.core;

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
