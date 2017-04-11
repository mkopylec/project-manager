package com.github.mkopylec.projectmanager.domain.values;

import static org.apache.commons.lang3.StringUtils.isBlank;

class EnumCreator {

    static <E extends Enum<E>> E createEnum(Class<E> type, String name, E defaultValue) {
        if (isBlank(name)) {
            return null;
        }
        try {
            return E.valueOf(type, name);
        } catch (IllegalArgumentException ex) {
            return defaultValue;
        }
    }

    private EnumCreator() {
    }
}
