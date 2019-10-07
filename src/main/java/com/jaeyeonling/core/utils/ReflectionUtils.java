package com.jaeyeonling.core.utils;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

@UtilityClass
public class ReflectionUtils {

    public void makeAccessible(final Constructor<?> constructor) {
        if (isNotPublic(constructor)) {
            constructor.setAccessible(true);
        }
    }

    private boolean isNotPublic(final Constructor<?> constructor) {
        return !Modifier.isPublic(constructor.getModifiers()) ||
                !Modifier.isPublic(constructor.getDeclaringClass().getModifiers());
    }
}
