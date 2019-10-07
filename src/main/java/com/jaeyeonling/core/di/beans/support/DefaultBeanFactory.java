package com.jaeyeonling.core.di.beans.support;

import com.jaeyeonling.core.di.beans.BeanDefinition;
import com.jaeyeonling.core.di.beans.BeanUtils;
import com.jaeyeonling.core.di.beans.factory.ConfigurableListableBeanFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class DefaultBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    private final Map<Class<?>, Object> beans = new HashMap<>();
    private final Map<Class<?>, BeanDefinition<?>> beanDefinitions = new HashMap<>();

    @Override
    public void preInstantiateSingletons() {
        getBeanClasses().forEach(this::getBean);
    }

    @Override
    public Set<Class<?>> getBeanClasses() {
        return Collections.unmodifiableSet(beanDefinitions.keySet());
    }

    @Override
    public void register(final Class<?> clazz,
                         final BeanDefinition beanDefinition) {
        log.debug("Register bean: {}", clazz);
        beanDefinitions.put(clazz, beanDefinition);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Optional<T> getBean(final Class<T> clazz) {
        final var bean = beans.get(clazz);
        if (Objects.nonNull(bean)) {
            return Optional.of((T) bean);
        }

        final var beanDefinition = beanDefinitions.get(clazz);
        if (Objects.isNull(beanDefinition)) {
            return Optional.empty();
        }

        final Optional<Class<?>> concreteClass = BeanFactoryUtils.findConcreteClass(clazz, getBeanClasses());
        if (concreteClass.isEmpty()) {
            return Optional.empty();
        }

        return (Optional<T>) inject(beanDefinition).stream()
                .peek(newBean -> beans.put(concreteClass.get(), newBean))
                .findFirst();
    }

    private Optional<Object> inject(final BeanDefinition<?> beanDefinition) {
        final var constructor = beanDefinition.getInjectConstructor();
        final var arguments = populateArguments(constructor.getParameterTypes());

        return Optional.of(BeanUtils.instantiateClass(constructor, arguments));
    }

    private Object[] populateArguments(final Class<?>... argumentTypes) {
        return Arrays.stream(argumentTypes)
                .peek(this::getBean)
                .map(this::getBean)
                .map(Optional::get)
                .toArray();
    }
}
