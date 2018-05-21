package com.github.mkopylec.projectmanager.domain.values;

public enum Requirement {

    OPTIONAL,
    RECOMMENDED,
    NECESSARY,
    INVALID;

    public boolean isNecessary() {
        return this == NECESSARY;
    }
}
