package com.github.mkopylec.projectmanager.common.support;

public class StringUtils {

    public static boolean isBlank(String string) {
        return string == null || string.isBlank();
    }

    private StringUtils() {
    }
}
