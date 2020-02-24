package com.github.mkopylec.projectmanager.core.common;

import org.slf4j.Logger;

import java.util.function.Supplier;

import static com.github.mkopylec.projectmanager.core.common.InputRequirementsValidator.requirements;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class UseCaseService {

    private final Logger logger = getLogger(this.getClass());

    protected <I> void executeUseCase(String useCase, I input, Runnable operation) {
        try {
            requirements()
                    .require(input)
                    .validate();
            logger.info("{} input received", input);
            operation.run();
        } catch (Exception e) {
            throw new ProjectManagerException(useCase, e);
        }
    }

    protected <O> O executeUseCase(String useCase, Supplier<O> operation) {
        try {
            var output = operation.get();
            logger.info("{} output returned", output);
            return output;
        } catch (Exception e) {
            throw new ProjectManagerException(useCase, e);
        }
    }

    protected <I, O> O executeUseCase(String useCase, I input, Supplier<O> operation) {
        try {
            requirements()
                    .require(input)
                    .validate();
            logger.info("{} input received", input);
            var output = operation.get();
            logger.info("{} output returned", output);
            return output;
        } catch (Exception e) {
            throw new ProjectManagerException(useCase, e);
        }
    }
}
