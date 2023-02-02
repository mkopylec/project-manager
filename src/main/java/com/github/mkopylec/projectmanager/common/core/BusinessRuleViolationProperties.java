package com.github.mkopylec.projectmanager.common.core;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.join;
import static java.util.Collections.unmodifiableList;

public class BusinessRuleViolationProperties {

    private final List<Property> properties = new ArrayList<>();

    private BusinessRuleViolationProperties(Property property) {
        properties.add(property);
    }

    public BusinessRuleViolationProperties append(String name, Object value) {
        properties.add(new Property(name, value));
        return this;
    }

    List<Property> get() {
        return unmodifiableList(properties);
    }

    @Override
    public String toString() {
        return join(", ", properties.stream().map(Property::toString).toList());
    }

    public static BusinessRuleViolationProperties properties(String name, Object value) {
        return new BusinessRuleViolationProperties(new Property(name, value));
    }

    record Property(String name, Object value) {

        @Override
        public String toString() {
            return name + "=" + value;
        }
    }
}
