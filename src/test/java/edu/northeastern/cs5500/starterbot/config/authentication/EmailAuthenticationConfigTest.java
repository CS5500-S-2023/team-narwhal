package edu.northeastern.cs5500.starterbot.config.authentication;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmailAuthenticationConfigTest {
    private EmailAuthenticationConfig testEmailConfig;

    @BeforeEach
    void setUp() {
        testEmailConfig = new EmailAuthenticationConfig();
    }

    @Test
    void testAsButtonFromId() {
        assertThat(testEmailConfig.asButton().getId()).isEqualTo("authenticate:email");
    }
}
