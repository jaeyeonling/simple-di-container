package com.jaeyeonling.core.di.beans.support;

import com.jaeyeonling.core.annotation.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BeanFactoryUtilsTest {

    private Set<Class<?>> preInstantiateBeans;

    @BeforeEach
    void setUp() {
        preInstantiateBeans = Set.of(InjectedConstructorInterface.class, InjectedConstructorClassNotFound.class,
                InjectedConstructorClass.class, Arg.class);
    }

    @Test
    void getInjectedConstructors() {
        final var injectedConstructors = BeanFactoryUtils.getInjectedConstructors(InjectedConstructorClass.class);
        assertThat(injectedConstructors).hasSize(2);
    }

    @Test
    void getInjectedConstructorsNotFound() {
        final var injectedConstructors = BeanFactoryUtils.getInjectedConstructors(
                InjectedConstructorClassNotFound.class);
        assertThat(injectedConstructors).isEmpty();
    }

    @Test
    void getInjectedConstructor() {
        final var injectedConstructor = BeanFactoryUtils.getInjectedConstructor(InjectedConstructorClass.class);
        assertThat(injectedConstructor).isPresent();
    }

    @Test
    void getInjectedConstructorNotFound() {
        final var injectedConstructor = BeanFactoryUtils.getInjectedConstructor(
                InjectedConstructorClassNotFound.class);
        assertThat(injectedConstructor).isNotPresent();
    }

    @Test
    void findConcreteClassByClass() {
        final var concreteClass = BeanFactoryUtils.findConcreteClass(InjectedConstructorClass.class,
                preInstantiateBeans);
        assertThat(concreteClass).isPresent();
        assertThat(concreteClass.get()).isEqualTo(InjectedConstructorClass.class);
    }

    @Test
    void findConcreteClassByInterface() {
        final var concreteClass = BeanFactoryUtils.findConcreteClass(InjectedConstructorInterface.class,
                preInstantiateBeans);
        assertThat(concreteClass).isPresent();
        assertThat(concreteClass.get()).isEqualTo(InjectedConstructorClass.class);
    }

    interface InjectedConstructorInterface { }

    class InjectedConstructorClassNotFound { }

    class InjectedConstructorClass implements InjectedConstructorInterface {

        @Inject
        public InjectedConstructorClass(final Arg arg1, final Arg arg2) { }

        @Inject
        public InjectedConstructorClass(final Arg arg1) { }
    }

    class Arg { }
}