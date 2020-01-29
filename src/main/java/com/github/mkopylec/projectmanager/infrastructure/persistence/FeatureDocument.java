package com.github.mkopylec.projectmanager.infrastructure.persistence;

class FeatureDocument {

    private String name;
    private String status;
    private String requirement;

    String getName() {
        return name;
    }

    FeatureDocument setName(String name) {
        this.name = name;
        return this;
    }

    String getStatus() {
        return status;
    }

    FeatureDocument setStatus(String status) {
        this.status = status;
        return this;
    }

    String getRequirement() {
        return requirement;
    }

    FeatureDocument setRequirement(String requirement) {
        this.requirement = requirement;
        return this;
    }
}
