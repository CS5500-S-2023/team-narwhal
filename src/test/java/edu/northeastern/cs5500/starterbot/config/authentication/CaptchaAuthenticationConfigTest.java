package edu.northeastern.cs5500.starterbot.config.authentication;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CaptchaAuthenticationConfigTest {

    private CaptchaAuthenticationConfig testCaptchaConfig;

    @BeforeEach
    void setUp() {
        testCaptchaConfig = new CaptchaAuthenticationConfig();
    }

    @Test
    void testButtonId() {
        assertThat(testCaptchaConfig.asButton().getId()).isEqualTo("authenticate:captcha");
    }
}
