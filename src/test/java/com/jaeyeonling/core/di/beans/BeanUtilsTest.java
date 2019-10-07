package com.jaeyeonling.core.di.beans;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BeanUtilsTest {

    @Test
    void instantiateClass() throws NoSuchMethodException {
        final var clazz = BeanUtils.instantiateClass(Clazz.class.getConstructor());
        assertThat(clazz).isInstanceOf(Clazz.class);
    }
}

class Clazz {
    public Clazz() { }
}