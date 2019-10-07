package com.jaeyeonling.core.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ReflectionUtilsTest {

    @Test
    void makeAccessible() throws Exception {
        final Constructor<Mock> constructor = Mock.class.getDeclaredConstructor();

        assertThatExceptionOfType(IllegalAccessException.class)
                .isThrownBy(constructor::newInstance);

        ReflectionUtils.makeAccessible(constructor);

        final Mock mock = constructor.newInstance();

        assertThat(mock).isNotNull();
    }
}

class Mock {
    private Mock() {
    }
}