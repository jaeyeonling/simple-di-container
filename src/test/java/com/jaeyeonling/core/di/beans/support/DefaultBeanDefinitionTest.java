package com.jaeyeonling.core.di.beans.support;

import com.jaeyeonling.core.annotation.Inject;
import com.jaeyeonling.core.di.beans.BeanDefinitionException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultBeanDefinitionTest {

    @Test
    void getInjectConstructor() {
        final var defaultBeanDefinition = new DefaultBeanDefinition<>(Clazz.class);
        final var injectConstructor = defaultBeanDefinition.getInjectConstructor();

        assertThat(injectConstructor).isNotNull();
    }

    @Test
    void getInjectConstructorNotFoundDefaultConstructor() {
        Assertions.assertThatExceptionOfType(BeanDefinitionException.class)
                .isThrownBy(() -> new DefaultBeanDefinition<>(NotFoundClazz.class));
    }

    class Clazz {
        @Inject
        public Clazz() {
        }
    }

    class NotFoundClazz {
    }
}