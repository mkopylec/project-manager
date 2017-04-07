package com.github.mkopylec.projectmanager.domain.values;

import static org.apache.commons.lang3.StringUtils.isBlank;

public enum JobPosition {

    _INVALID,
    DEVELOPER,
    SCRUM_MASTER,
    PRODUCT_OWNER;

    public static JobPosition createJobPosition(String jobPosition) {
        if (isBlank(jobPosition)) {
            return null;
        }
        try {
            return valueOf(jobPosition);
        } catch (IllegalArgumentException ex) {
            return _INVALID;
        }
    }
}
