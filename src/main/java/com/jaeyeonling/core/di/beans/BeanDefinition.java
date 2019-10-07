package com.jaeyeonling.core.di.beans;

import java.lang.reflect.Constructor;

@FunctionalInterface
public interface BeanDefinition<T> {

    Constructor<T> getInjectConstructor();
}
