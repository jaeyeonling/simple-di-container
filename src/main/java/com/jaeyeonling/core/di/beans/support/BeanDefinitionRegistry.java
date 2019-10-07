package com.jaeyeonling.core.di.beans.support;

import com.jaeyeonling.core.di.beans.BeanDefinition;

@FunctionalInterface
public interface BeanDefinitionRegistry {

    void register(final Class<?> clazz,
                  final BeanDefinition beanDefinition);
}
