package com.jaeyeonling.core.di.beans;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum DefaultTypeValue {

    BOOLEAN(boolean.class, false),
    BYTE(byte.class, (byte) 0),
    SHORT(short.class, (short) 0),
    INT(int.class, 0),
    LONG(long.class, (long) 0);

    private final Class<?> clazz;
    private final Object value;

    public static Object from(final Class<?> clazz) {
        return Arrays.stream(values())
                .filter(defaultTypeValue -> defaultTypeValue.clazz == clazz)
                .findFirst()
                .map(defaultTypeValue -> defaultTypeValue.value)
                .orElse(null);
    }
}
