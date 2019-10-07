package com.jaeyeonling.core.di.beans.support;

@FunctionalInterface
public interface BeanDefinitionReader {

    void loadBeanDefinitions(final Class<?>... classes);
}
