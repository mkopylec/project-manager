package com.github.mkopylec.projectmanager.common.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toCollection;

public class ListUtils {

    public static <S, T> List<T> mapToUnmodifiable(List<S> list, Function<S, T> nonNullableMapper) {
        if (list == null) {
            return null;
        }
        return list.stream().map(it -> it != null ? nonNullableMapper.apply(it) : null).toList();
    }

    public static <T> List<T> cleanToUnmodifiable(List<T> list) {
        return clean(list).toList();
    }

    public static <T> List<T> cleanToModifiable(List<T> list) {
        return clean(list).collect(toCollection(ArrayList::new));
    }

    private static <T> Stream<T> clean(List<T> list) {
        return ofNullable(list).orElse(emptyList()).stream().filter(Objects::nonNull);
    }

    private ListUtils() {
    }
}
