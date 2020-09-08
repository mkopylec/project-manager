package com.github.mkopylec.projectmanager.core;

import com.github.mkopylec.projectmanager.core.common.Committable;

import java.util.List;

class UnitOfWork implements AutoCloseable {

    private List<Committable> committables;

    UnitOfWork(List<Committable> committables) {
        this.committables = committables;
    }

    void commit() {
        committables.forEach(Committable::commit);
    }

    @Override
    public void close() {
        committables.forEach(Committable::dispose);
    }
}
