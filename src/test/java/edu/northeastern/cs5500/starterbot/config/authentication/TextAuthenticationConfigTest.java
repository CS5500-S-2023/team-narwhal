package edu.northeastern.cs5500.starterbot.config.authentication;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TextAuthenticationConfigTest {
    private TextAuthenticationConfig testTextConfig;

    @BeforeEach
    void setUp() {
        testTextConfig = new TextAuthenticationConfig();
    }

    @Test
    void testAsButtonFromButtonId() {
        assertThat(testTextConfig.asButton().getId()).isEqualTo("authenticate:text");
    }
}
