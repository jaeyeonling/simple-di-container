package com.jaeyeonling.core.di.context.scanner;

import com.jaeyeonling.core.annotation.Component;
import com.jaeyeonling.core.annotation.Configuration;
import com.jaeyeonling.core.di.beans.support.BeanDefinitionRegistry;
import com.jaeyeonling.core.di.beans.support.DefaultBeanDefinition;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Set;

public class ClasspathBeanDefinitionScanner implements BeanDefinitionScanner {

    private static final Set<Class<? extends Annotation>> TARGET_ANNOTATIONS = Set.of(Configuration.class,
            Component.class);

    private final BeanDefinitionRegistry beanDefinitionRegistry;
    private final Reflections reflections;

    public ClasspathBeanDefinitionScanner(final BeanDefinitionRegistry beanDefinitionRegistry,
                                          final Object... basePackages) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
        this.reflections = new Reflections(basePackages);
    }

    @Override
    public void doScan() {
        TARGET_ANNOTATIONS.stream()
                .map(reflections::getTypesAnnotatedWith)
                .flatMap(Collection::stream)
                .distinct()
                .forEach(clazz -> beanDefinitionRegistry.register(clazz, new DefaultBeanDefinition<>(clazz)));
    }
}
