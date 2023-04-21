package edu.northeastern.cs5500.starterbot.config.authentication;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Test class for {@link AuthenticationType}. */
class AuthenticationTypeTest {

    private AuthenticationType captcha;
    private AuthenticationType email;
    private AuthenticationType text;

    @BeforeEach
    void setUp() {
        captcha = AuthenticationType.CAPTCHA;
        email = AuthenticationType.EMAIL;
        text = AuthenticationType.TEXT;
    }

    @Test
    void values() {
        assertThat(AuthenticationType.values())
                .asList()
                .containsExactly(
                        AuthenticationType.CAPTCHA,
                        AuthenticationType.EMAIL,
                        AuthenticationType.TEXT);
    }

    @Test
    void valueOf() {
        assertThat(AuthenticationType.valueOf("CAPTCHA")).isEqualTo(AuthenticationType.CAPTCHA);
        assertThat(AuthenticationType.valueOf("EMAIL")).isEqualTo(AuthenticationType.EMAIL);
        assertThat(AuthenticationType.valueOf("TEXT")).isEqualTo(AuthenticationType.TEXT);
    }
}
