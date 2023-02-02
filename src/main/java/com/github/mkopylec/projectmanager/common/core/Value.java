package com.github.mkopylec.projectmanager.common.core;

import static java.util.Objects.hash;

public abstract class Value<T> {

    private final T value;

    protected Value(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Value<?> value1 = (Value<?>) o;
        return value.equals(value1.value);
    }

    @Override
    public int hashCode() {
        return hash(value);
    }
}
