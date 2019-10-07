package com.jaeyeonling.core.di.context;

import java.util.List;
import java.util.Optional;

public interface ApplicationContext {

    <T> Optional<T> getBean(Class<T> clazz);

    <T> List<? super T> getBeans(Class<T> clazz);
}
