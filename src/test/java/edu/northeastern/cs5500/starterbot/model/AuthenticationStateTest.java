package edu.northeastern.cs5500.starterbot.model;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Test class for {@link AuthenticationChallenge}. */
public class AuthenticationStateTest {

    private AuthenticationState unknown;
    private AuthenticationState waiting;
    private AuthenticationState incorrect;
    private AuthenticationState verified;
    private AuthenticationState tooManyAttempts;
    private AuthenticationState failedToSend;

    @BeforeEach
    void setUp() {
        unknown = AuthenticationState.UNKNOWN;
        waiting = AuthenticationState.WAITING_FOR_RESPONSE;
        incorrect = AuthenticationState.INCORRECT_RESPONSE;
        verified = AuthenticationState.VERIFIED;
        tooManyAttempts = AuthenticationState.TOO_MANY_ATTEMPTS;
        failedToSend = AuthenticationState.FAILED_TO_SEND;
    }

    @Test
    void isTerminal() {
        assertThat(unknown.isTerminal()).isFalse();
        assertThat(waiting.isTerminal()).isFalse();
        assertThat(incorrect.isTerminal()).isFalse();
        assertThat(verified.isTerminal()).isTrue();
        assertThat(tooManyAttempts.isTerminal()).isTrue();
        assertThat(failedToSend.isTerminal()).isTrue();
    }

    @Test
    void values() {
        assertThat(AuthenticationState.values())
                .asList()
                .containsExactly(
                        AuthenticationState.UNKNOWN,
                        AuthenticationState.WAITING_FOR_RESPONSE,
                        AuthenticationState.INCORRECT_RESPONSE,
                        AuthenticationState.VERIFIED,
                        AuthenticationState.TOO_MANY_ATTEMPTS,
                        AuthenticationState.FAILED_TO_SEND);
    }

    @Test
    void valueOf() {
        assertThat(AuthenticationState.valueOf("UNKNOWN")).isEqualTo(AuthenticationState.UNKNOWN);
        assertThat(AuthenticationState.valueOf("WAITING_FOR_RESPONSE"))
                .isEqualTo(AuthenticationState.WAITING_FOR_RESPONSE);
        assertThat(AuthenticationState.valueOf("INCORRECT_RESPONSE"))
                .isEqualTo(AuthenticationState.INCORRECT_RESPONSE);
        assertThat(AuthenticationState.valueOf("VERIFIED")).isEqualTo(AuthenticationState.VERIFIED);
        assertThat(AuthenticationState.valueOf("TOO_MANY_ATTEMPTS"))
                .isEqualTo(AuthenticationState.TOO_MANY_ATTEMPTS);
        assertThat(AuthenticationState.valueOf("FAILED_TO_SEND"))
                .isEqualTo(AuthenticationState.FAILED_TO_SEND);
    }
}
