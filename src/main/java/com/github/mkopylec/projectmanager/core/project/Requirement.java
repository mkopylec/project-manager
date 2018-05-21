package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.common.InvalidStateSupportingEnum;

enum Requirement implements InvalidStateSupportingEnum {

    OPTIONAL,
    RECOMMENDED,
    NECESSARY,
    INVALID;

    boolean isNecessary() {
        return this == NECESSARY;
    }
}
