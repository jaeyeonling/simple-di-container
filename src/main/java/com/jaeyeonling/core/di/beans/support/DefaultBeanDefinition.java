package com.jaeyeonling.core.di.beans.support;

import com.jaeyeonling.core.di.beans.BeanDefinition;
import com.jaeyeonling.core.di.beans.BeanDefinitionException;
import lombok.Getter;

import java.lang.reflect.Constructor;

public class DefaultBeanDefinition<T> implements BeanDefinition<T> {

    private final Class<T> beanClass;

    @Getter
    private final Constructor<T> injectConstructor;

    public DefaultBeanDefinition(final Class<T> clazz) {
        beanClass = clazz;
        injectConstructor = initializeConstructor();
    }

    @SuppressWarnings("unchecked")
    private Constructor<T> initializeConstructor() {
        return (Constructor<T>) BeanFactoryUtils.getInjectedConstructor(beanClass)
                .orElseGet(this::getDefaultConstructor);
    }

    private Constructor getDefaultConstructor() {
        try {
            return beanClass.getConstructor();
        } catch (final NoSuchMethodException e) {
            throw new BeanDefinitionException("Not found default constructor", e);
        }
    }
}
