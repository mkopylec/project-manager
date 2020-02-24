package com.github.mkopylec.projectmanager.core.project.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("identifier", identifier)
                .append("onlyNecessaryFeatureDone", onlyNecessaryFeatureDone)
                .toString();
    }

    private ProjectEndingCondition() {
    }
}
