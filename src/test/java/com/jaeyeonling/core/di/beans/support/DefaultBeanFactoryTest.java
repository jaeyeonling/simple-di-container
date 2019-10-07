package com.jaeyeonling.core.di.beans.support;

import com.jaeyeonling.core.annotation.Component;
import com.jaeyeonling.core.annotation.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultBeanFactoryTest {

    private DefaultBeanFactory defaultBeanFactory;

    @BeforeEach
    void setUp() {
        defaultBeanFactory = new DefaultBeanFactory();
    }

    @Test
    void preInstantiateSingletonsEmpty() {
        assertThat(defaultBeanFactory.getBeanClasses()).isEmpty();
        defaultBeanFactory.preInstantiateSingletons();
        assertThat(defaultBeanFactory.getBeanClasses()).isEmpty();
    }

    @Test
    void preInstantiateSingletonsRegister() {
        assertThat(defaultBeanFactory.getBeanClasses()).isEmpty();
        defaultBeanFactory.register(Clazz.class, new DefaultBeanDefinition<>(Clazz.class));
        defaultBeanFactory.preInstantiateSingletons();
        assertThat(defaultBeanFactory.getBeanClasses()).hasSize(1);
    }

    @Test
    void register() {
        assertThat(defaultBeanFactory.getBeanClasses()).isEmpty();
        defaultBeanFactory.register(Clazz.class, new DefaultBeanDefinition<>(Clazz.class));
        assertThat(defaultBeanFactory.getBeanClasses()).hasSize(1);
    }

    @Test
    void getBean() {
        assertThat(defaultBeanFactory.getBean(Clazz.class)).isNotPresent();
        defaultBeanFactory.register(Clazz.class, new DefaultBeanDefinition<>(Clazz.class));
        assertThat(defaultBeanFactory.getBean(Clazz.class)).isPresent();
    }

    @Test
    void getBeanEmpty() {
        assertThat(defaultBeanFactory.getBean(Clazz.class)).isNotPresent();
    }
}

@Component
class Clazz {

    @Inject
    public Clazz() {
    }
}