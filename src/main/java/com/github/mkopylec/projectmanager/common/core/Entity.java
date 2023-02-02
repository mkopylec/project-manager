package com.github.mkopylec.projectmanager.common.core;

import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;

public abstract class Entity<ID> {

    private final ID id;

    protected Entity(ID id) {
        this.id = requireNonNull(id, "No entity ID");
    }

    ID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return id.equals(entity.id);
    }

    @Override
    public int hashCode() {
        return hash(id);
    }
}
