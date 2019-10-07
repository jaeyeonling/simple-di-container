package com.jaeyeonling.core.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class AssertTest {

    @Test
    void notNullSuccess() {
        Assert.notNull("", "is not null");
        Assert.notNull(new Object(), "is not null");
        Assert.notNull(100, "is not null");
    }

    @Test
    void notNullFail() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Assert.notNull(null, "is null"));
    }

    @Test
    void isTrueSuccess() {
        Assert.isTrue(true, "is True");
    }

    @Test
    void isTrueFail() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Assert.isTrue(false, "is not True"));
    }
}