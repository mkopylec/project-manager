package com.github.mkopylec.projectmanager.project;

public enum Requirement {

    OPTIONAL,
    RECOMMENDED,
    NECESSARY;

    public boolean isNecessary() {
        return this == NECESSARY;
    }
}
