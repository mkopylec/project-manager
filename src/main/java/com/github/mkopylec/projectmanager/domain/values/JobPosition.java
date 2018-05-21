package com.github.mkopylec.projectmanager.domain.values;

public enum JobPosition {

    DEVELOPER,
    SCRUM_MASTER,
    PRODUCT_OWNER,
    INVALID;

    public boolean isInvalid() {
        return this == INVALID;
    }
}
