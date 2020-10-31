package com.github.mkopylec.projectmanager.core.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class Utilities {

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            return object.toString().isBlank();
        }
        if (object instanceof Collection) {
            return ((Collection<?>) object).isEmpty();
        }
        return false;
    }

    public static <E> List<E> neverNull(List<E> list) {
        return list == null ? new ArrayList<>() : new ArrayList<>(list);
    }

    public static <E> boolean none(Predicate<E> predicate, List<E> list) {
        return neverNull(list).stream().noneMatch(predicate);
    }

    public static boolean noneEmpty(List<?> list) {
        return none(Utilities::isEmpty, list);
    }

    public static <E> boolean all(Predicate<E> predicate, List<E> list) {
        return neverNull(list).stream().allMatch(predicate);
    }

    public static <S, D> List<D> mapElements(List<S> source, Function<S, D> destinationCreator) {
        return neverNull(source).stream().map(destinationCreator).collect(toList());
    }

    public static boolean isValueOfEnum(String value, Class<? extends Enum<?>> type) {
        return stream(type.getEnumConstants()).anyMatch(it -> it.name().equals(value));
    }

    private Utilities() {
    }
}
