package com.github.mkopylec.projectmanager.core.common;

import org.slf4j.Logger;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class Repository<E> {

    private final Logger logger = getLogger(this.getClass());

    protected void logFound(E entity) {
        logger.info("{} found", entity);
    }

    protected void logFound(List<E> entities) {
        logger.info("{} found", entities);
    }

    protected void logSaved(E entity) {
        logger.info("{} saved", entity);
    }
}
