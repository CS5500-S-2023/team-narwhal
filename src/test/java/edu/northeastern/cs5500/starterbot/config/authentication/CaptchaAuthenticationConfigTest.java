package edu.northeastern.cs5500.starterbot.config.authentication;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class CaptchaAuthenticationConfigTest {
    CaptchaAuthenticationConfig testConfig = new CaptchaAuthenticationConfig();

    @Test
    void testName() {
        assertEquals(testConfig.name, "authenticate");
    }

    @Test
    void testId() {
        assertEquals(testConfig.getId(), "captcha");
    }

    @Test
    void testLabel() {
        assertEquals(testConfig.getLabel(), "CAPTCHA");
    }

    @Test
    void testButtonId() {
        assertEquals(testConfig.asButton().getId(), "authenticate:captcha");
    }
}
