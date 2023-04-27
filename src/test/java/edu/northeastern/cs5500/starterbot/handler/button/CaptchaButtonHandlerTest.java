package edu.northeastern.cs5500.starterbot.handler.button;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CaptchaButtonHandlerTest {
    private CaptchaButtonHandler testCaptchaButtonHandler;

    @BeforeEach
    void setUp() {
        testCaptchaButtonHandler = new CaptchaButtonHandler();
    }

    @Test
    void getName() {
        assertThat(testCaptchaButtonHandler.getName()).isEqualTo("authenticate");
    }

    @Test
    void isHandlerFor() {
        assertThat(testCaptchaButtonHandler.isHandlerFor("authenticate:captcha")).isTrue();
        assertThat(testCaptchaButtonHandler.isHandlerFor("authenticate:text")).isFalse();
        assertThat(testCaptchaButtonHandler.isHandlerFor("")).isFalse();
    }

    @Test
    void handleButtonInteraction() {}

    @Test
    void generateCaptchaFile() {}
}
