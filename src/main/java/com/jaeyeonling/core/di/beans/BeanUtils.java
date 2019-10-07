package com.jaeyeonling.core.di.beans;

import com.jaeyeonling.core.utils.Assert;
import com.jaeyeonling.core.utils.ReflectionUtils;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@UtilityClass
public class BeanUtils {

    public <T> T instantiateClass(final Constructor<T> constructor,
                                  final Object... arguments) {
        Assert.notNull(constructor, "Constructor must not be null");

        ReflectionUtils.makeAccessible(constructor);

        final var parameterTypes = constructor.getParameterTypes();

        Assert.isTrue(arguments.length <= parameterTypes.length,
                "Can't specify more arguments than constructor parameters");

        final var argumentsWithDefaultValues = getArgumentsWithDefaultValues(parameterTypes, arguments);
        return newInstance(constructor, argumentsWithDefaultValues);
    }

    private Object[] getArgumentsWithDefaultValues(final Class<?>[] parameterTypes,
                                                   final Object[] arguments) {
        final var argumentsWithDefaultValues = new Object[arguments.length];
        for (var i = 0; i < arguments.length; i++) {
            final var argument = arguments[i];

            if (Objects.nonNull(argument)) {
                argumentsWithDefaultValues[i] = argument;
                continue;
            }

            final var parameterType = parameterTypes[i];
            if (parameterType.isPrimitive()) {
                argumentsWithDefaultValues[i] = DefaultTypeValue.from(parameterType);
            }
        }

        return argumentsWithDefaultValues;
    }

    private <T> T newInstance(final Constructor<T> constructor,
                              final Object[] argumentsWithDefaultValues) {
        try {
            return constructor.newInstance(argumentsWithDefaultValues);
        } catch (final InstantiationException e) {
            throw new BeanInstantiationException(constructor, "Is it an abstract class?", e);
        } catch (final IllegalAccessException e) {
            throw new BeanInstantiationException(constructor, "Is the constructor accessible?", e);
        } catch (final InvocationTargetException e) {
            throw new BeanInstantiationException(constructor, "Constructor throw exception", e.getTargetException());
        }
    }
}
