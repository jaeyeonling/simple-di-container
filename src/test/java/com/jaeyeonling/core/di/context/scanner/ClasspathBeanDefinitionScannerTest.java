package com.jaeyeonling.core.di.context.scanner;

import com.jaeyeonling.core.annotation.Component;
import com.jaeyeonling.core.annotation.Configuration;
import com.jaeyeonling.core.annotation.Inject;
import com.jaeyeonling.core.di.beans.support.DefaultBeanFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClasspathBeanDefinitionScannerTest {

    private DefaultBeanFactory defaultBeanFactory;

    @BeforeEach
    void setUp() {
        defaultBeanFactory = new DefaultBeanFactory();
    }

    @Test
    void doScan() {
        final var classpathBeanDefinitionScanner = new ClasspathBeanDefinitionScanner(defaultBeanFactory,
                "com.jaeyeonling.core.di.context.scanner");

        assertThat(defaultBeanFactory.getBeanClasses()).isEmpty();
        classpathBeanDefinitionScanner.doScan();
        assertThat(defaultBeanFactory.getBeanClasses()).hasSize(2);
    }

    @Test
    void doScanNotFound() {
        final var classpathBeanDefinitionScanner = new ClasspathBeanDefinitionScanner(defaultBeanFactory,
                "not.found");

        assertThat(defaultBeanFactory.getBeanClasses()).isEmpty();
        classpathBeanDefinitionScanner.doScan();
        assertThat(defaultBeanFactory.getBeanClasses()).isEmpty();
    }

    @Component
    class Clazz1 {

        @Inject
        public Clazz1() {
        }
    }

    @Configuration
    class Clazz2 {

        @Inject
        public Clazz2() {
        }
    }

    class NotComponentClazz {
    }
}

