package com.jaeyeonling.core.di.context;

import com.jaeyeonling.core.annotation.ComponentScan;
import com.jaeyeonling.core.di.beans.support.DefaultBeanFactory;
import com.jaeyeonling.core.di.context.scanner.ClasspathBeanDefinitionScanner;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class AnnotationConfigApplicationContext implements ApplicationContext {

    private final DefaultBeanFactory beanFactory;

    public AnnotationConfigApplicationContext(final Class<?>... annotatedClasses) {
        beanFactory = new DefaultBeanFactory();

        scan(annotatedClasses);

        beanFactory.preInstantiateSingletons();
    }

    @Override
    public <T> Optional<T> getBean(final Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    @Override
    public <T> List<? super T> getBeans(final Class<T> clazz) {
        return beanFactory.getBeanClasses()
                .stream()
                .filter(clazz::isAssignableFrom)
                .map(beanFactory::getBean)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private void scan(final Class<?>... annotatedClasses) {
        final var basePackages = findBasePackages(annotatedClasses);
        final var scanner = new ClasspathBeanDefinitionScanner(beanFactory, basePackages);

        scanner.doScan();
    }

    private Object[] findBasePackages(final Class<?>... annotatedClasses) {
        return Arrays.stream(annotatedClasses)
                .map(clazz -> clazz.getAnnotation(ComponentScan.class))
                .filter(Objects::nonNull)
                .map(ComponentScan::value)
                .flatMap(Arrays::stream)
                .peek(basePackage -> log.debug("Component Scan [basePackage={}]", basePackage))
                .toArray();
    }
}
