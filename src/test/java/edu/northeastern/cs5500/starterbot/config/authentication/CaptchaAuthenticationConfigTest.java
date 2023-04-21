package edu.northeastern.cs5500.starterbot.config.authentication;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

public class CaptchaAuthenticationConfigTest {

    CaptchaAuthenticationConfig testConfig = new CaptchaAuthenticationConfig();

    @Test
    void testName() {
        assertThat(AuthenticationConfig.name).isEqualTo("authenticate");
    }

    @Test
    void testGetId() {
        assertThat(testConfig.getId()).isEqualTo("captcha");
    }

    @Test
    void testGetLabel() {
        assertThat(testConfig.getLabel()).isEqualTo("CAPTCHA");
    }

    @Test
    void testButtonId() {
        assertThat(testConfig.asButton().getId()).isEqualTo("authenticate:captcha");
    }
}
