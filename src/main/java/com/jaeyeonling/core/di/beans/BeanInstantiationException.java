package com.jaeyeonling.core.di.beans;

import java.lang.reflect.Constructor;

public class BeanInstantiationException extends RuntimeException {

    private static final String MESSAGE_FORMAT = "Failed to instantiate [%s]: %s";

    public BeanInstantiationException(final Constructor<?> constructor,
                                      final String message,
                                      final Throwable cause) {
        super(String.format(MESSAGE_FORMAT, constructor.getDeclaringClass().getName(), message), cause);
    }
}
