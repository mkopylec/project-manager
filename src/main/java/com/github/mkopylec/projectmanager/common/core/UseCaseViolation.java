package com.github.mkopylec.projectmanager.common.core;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Enum.valueOf;

public abstract class UseCaseViolation extends RuntimeException {

    private final CommonViolationCode code;
    private final Map<String, Object> properties;

    protected UseCaseViolation(CommonViolationCode code, BusinessRuleViolation violation) {
        super(code.name() + " " + violation.getMessage(), violation);
        this.code = code;
        properties = new HashMap<>();
        violation.getProperties().get().forEach(it -> properties.put(it.name(), it.value()));
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    protected <C extends Enum<C> & CommonViolationCode> C getCode(Class<C> type) {
        return valueOf(type, code.name());
    }

    public interface CommonViolationCode {

        String name();
    }
}
