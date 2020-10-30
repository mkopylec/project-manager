package com.github.mkopylec.projectmanager.utils;

import org.mockito.ArgumentMatcher;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.slf4j.LoggerFactory.getLogger;

public class Exactly<T> implements ArgumentMatcher<T> {

    private static final Logger logger = getLogger(Exactly.class);

    private T expected;
    private String[] ignoredFields;

    public static <T> T exactly(T expected, String... ignoredFields) {
        return argThat(new Exactly<>(expected, ignoredFields));
    }

    private Exactly(T expected, String... ignoredFields) {
        this.expected = expected;
        this.ignoredFields = ignoredFields;
    }

    @Override
    public boolean matches(T argument) {
        if (expected == null && argument == null) {
            return true;
        }
        if (expected == null || argument == null) {
            return false;
        }
        try {
            assertThat(expected)
                    .usingRecursiveComparison()
                    .withStrictTypeChecking()
                    .ignoringCollectionOrder()
                    .ignoringFields(ignoredFields)
                    .isEqualTo(argument);
            return true;
        } catch (AssertionError e) {
            if (expected.getClass().equals(argument.getClass())) logger.error(e.getMessage(), e);
            return false;
        }
    }
}
