package com.github.mkopylec.projectmanager.api.dto;

import java.util.List;

public class Failure {

    private List<String> violations;

    public List<String> getViolations() {
        return violations;
    }

    public Failure setViolations(List<String> violations) {
        this.violations = violations;
        return this;
    }
}
