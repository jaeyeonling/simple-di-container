package com.jaeyeonling.core.di.context.application;

import com.jaeyeonling.core.annotation.Component;
import com.jaeyeonling.core.annotation.ComponentScan;
import com.jaeyeonling.core.annotation.Inject;
import com.jaeyeonling.core.di.context.AnnotationConfigApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AnnotationConfigApplicationContextTest {

    private AnnotationConfigApplicationContext annotationConfigApplicationContext;

    @BeforeEach
    void setUp() {
        annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Config.class);
    }

    @Test
    void getBeanDeep() {
        assertThat(annotationConfigApplicationContext.getBean(DefaultComponent.class)).isPresent();

        final var message = annotationConfigApplicationContext.getBean(Component1.class)
                .map(Component1::getComponent2)
                .map(Component2::getComponent3)
                .map(Component3::getComponent4)
                .map(Component4::getComponent5)
                .map(Component5::message)
                .orElseThrow();

        assertThat(message).isEqualTo("DI");
    }

    @Test
    void getBeans() {
        assertThat(annotationConfigApplicationContext.getBeans(ComponentInterface.class)).hasSize(5);
    }
}

@ComponentScan("com.jaeyeonling.core.di.context.application")
class Config { }

@Component
class DefaultComponent {

    @Inject
    public DefaultComponent() { }
}

@Component
class Component1 implements ComponentInterface {

    private Component2 component2;

    @Inject
    public Component1(final Component2 component2) {
        this.component2 = component2;
    }

    public Component2 getComponent2() {
        return component2;
    }
}

@Component
class Component2 implements ComponentInterface {

    private Component3 component3;

    @Inject
    public Component2(final Component3 component3) {
        this.component3 = component3;
    }

    public Component3 getComponent3() {
        return component3;
    }
}

@Component
class Component3 implements ComponentInterface {

    private Component4 component4;

    @Inject
    public Component3(final Component4 component4) {
        this.component4 = component4;
    }

    public Component4 getComponent4() {
        return component4;
    }
}

@Component
class Component4 implements ComponentInterface {

    private Component5 component5;

    @Inject
    public Component4(final Component5 component5) {
        this.component5 = component5;
    }

    public Component5 getComponent5() {
        return component5;
    }
}

@Component
class Component5 implements ComponentInterface {

    @Inject
    public Component5() { }

    public String message() {
        return "DI";
    }
}

interface ComponentInterface { }