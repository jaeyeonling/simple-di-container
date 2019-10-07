package com.jaeyeonling.core.utils;

import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class Assert {

    public void notNull(final Object object,
                        final String message) {
        if (Objects.isNull(object)) {
            throw new IllegalArgumentException(message);
        }
    }

    public void isTrue(final boolean expression,
                       final String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }
}
