package com.github.mkopylec.projectmanager.core.common;

import static com.github.mkopylec.projectmanager.core.common.InvalidStateSupportingEnum.INVALID_STATE_VALUE;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class InvalidStateSupportingEnumMapper {

    public static <T extends Enum<T> & InvalidStateSupportingEnum> T mapTo(Class<T> type, String value) {
        if (isBlank(value)) {
            return null;
        }
        try {
            return Enum.valueOf(type, value);
        } catch (Exception e) {
            return Enum.valueOf(type, INVALID_STATE_VALUE);
        }
    }
}
