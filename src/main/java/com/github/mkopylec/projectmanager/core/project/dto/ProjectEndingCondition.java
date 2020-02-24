package com.github.mkopylec.projectmanager.core.project.dto;

public class ProjectEndingCondition {

    private String identifier;
    private boolean onlyNecessaryFeatureDone;

    public ProjectEndingCondition(String identifier, boolean onlyNecessaryFeatureDone) {
        this.identifier = identifier;
        this.onlyNecessaryFeatureDone = onlyNecessaryFeatureDone;
    }

    public String getIdentifier() {
        return identifier;
    }

    public boolean isOnlyNecessaryFeatureDone() {
        return onlyNecessaryFeatureDone;
    }

    private ProjectEndingCondition() {
    }
}
