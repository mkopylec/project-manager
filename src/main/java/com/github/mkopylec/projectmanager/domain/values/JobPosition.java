package com.github.mkopylec.projectmanager.domain.values;

import static org.apache.commons.lang3.StringUtils.isBlank;

public enum JobPosition {

    INVALID,
    DEVELOPER,
    SCRUM_MASTER,
    PRODUCT_OWNER;

    @Override
    public String toString() {
        return name().toLowerCase().replace('_', ' ');
    }

    public static JobPosition createJobPosition(String jobPosition) {
        if (isBlank(jobPosition)) {
            return null;
        }
        String normalizedJobPosition = jobPosition.toUpperCase().replace(' ', '_');
        try {
            return valueOf(normalizedJobPosition);
        } catch (IllegalArgumentException ex) {
            return INVALID;
        }
    }
}
