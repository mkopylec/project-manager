package com.github.mkopylec.projectmanager.core.common;

public interface InvalidStateSupportingEnum {

    String INVALID_STATE_VALUE = "INVALID";

    default boolean isInvalid() {
        return toString().equals(INVALID_STATE_VALUE);
    }
}
