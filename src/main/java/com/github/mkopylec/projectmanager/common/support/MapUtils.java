package com.github.mkopylec.projectmanager.common.support;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;

public class MapUtils {

    public static <K, V> Map<K, V> toUnmodifiable(Map<K, V> map) {
        return map == null ? emptyMap() : unmodifiableMap(map);
    }

    private MapUtils() {
    }
}
