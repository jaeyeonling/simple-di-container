package com.jaeyeonling.core.di.beans.factory;

import java.util.Optional;
import java.util.Set;

public interface BeanFactory {

    Set<Class<?>> getBeanClasses();

    <T> Optional<T> getBean(final Class<T> clazz);
}
