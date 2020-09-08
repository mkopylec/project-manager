package com.github.mkopylec.projectmanager.core.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static java.lang.Enum.valueOf;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

public class Utilities {

    public static boolean allEmpty(Object... objects) {
        return of(objects).allMatch(Utilities::isEmpty);
    }

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

    public static <O, I> O ifNotEmpty(I object, Function<I, O> retriever) {
        return isNotEmpty(object) ? retriever.apply(object) : null;
    }

    public static <E> List<E> neverNull(List<E> list) {
        return list == null ? new ArrayList<>() : new ArrayList<>(list);
    }

    public static <S, D> List<D> mapElements(List<S> source, Function<S, D> destinationCreator) {
        return neverNull(source).stream().map(destinationCreator).collect(toList());
    }

    public static <S extends Enum<S>, D extends Enum<D>> D convertEnum(S source, Class<D> destination) {
        return isEmpty(source) ? null : valueOf(destination, source.name());
    }

    public static <D extends Enum<D>> D toEnum(String source, Class<D> destination) {
        return isEmpty(source) ? null : valueOf(destination, source);
    }

    private Utilities() {
    }
}
