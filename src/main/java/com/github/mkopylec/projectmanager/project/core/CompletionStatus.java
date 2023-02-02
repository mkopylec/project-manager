package com.github.mkopylec.projectmanager.project.core;

import com.github.mkopylec.projectmanager.common.core.Value;

import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolation.requireNoBusinessRuleViolation;
import static com.github.mkopylec.projectmanager.common.core.BusinessRuleViolationProperties.properties;

public abstract sealed class CompletionStatus extends Value<String> {

    private CompletionStatus(String value) {
        super(value);
    }

    public static CompletionStatus fromPersistentState(String value) {
        return requireNoBusinessRuleViolation(() -> switch (value) {
            case ToDo.TO_DO -> new ToDo();
            case InProgress.IN_PROGRESS -> new InProgress();
            case Done.DONE -> new Done();
            default -> throw new InvalidCompletionStatus(value);
        });
    }

    static final class ToDo extends CompletionStatus {

        private static final String TO_DO = "To do";

        ToDo() {
            super(TO_DO);
        }
    }

    static final class InProgress extends CompletionStatus {

        private static final String IN_PROGRESS = "In progress";

        InProgress() {
            super(IN_PROGRESS);
        }
    }

    static final class Done extends CompletionStatus {

        private static final String DONE = "Done";

        Done() {
            super(DONE);
        }
    }

    static final class InvalidCompletionStatus extends ProjectBusinessRuleViolation {

        private InvalidCompletionStatus(String status) {
            super(properties("status", status));
        }
    }
}
