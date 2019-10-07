package com.jaeyeonling.core.di.beans.support;

import com.jaeyeonling.core.annotation.Inject;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.util.Optional;
import java.util.Set;

@Slf4j
@UtilityClass
public class BeanFactoryUtils {
    
    @SuppressWarnings("unchecked")
    public Set<Constructor> getInjectedConstructors(final Class<?> clazz) {
        return ReflectionUtils.getAllConstructors(clazz, ReflectionUtils.withAnnotation(Inject.class));
    }

    public Optional<Constructor> getInjectedConstructor(final Class<?> clazz) {
        return getInjectedConstructors(clazz).stream()
                .findFirst();
    }


    public Optional<Class<?>> findConcreteClass(final Class<?> injectedClass,
                                                final Set<Class<?>> preInstantiateBeans) {
        if (!injectedClass.isInterface()) {
            return Optional.of(injectedClass);
        }

        return preInstantiateBeans.stream()
                .filter(clazz -> Set.of(clazz.getInterfaces()).contains(injectedClass))
                .findFirst();
    }
}
