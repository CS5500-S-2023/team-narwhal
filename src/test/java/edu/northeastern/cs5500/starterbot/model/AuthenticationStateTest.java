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
                        unknown, waiting, incorrect, verified, tooManyAttempts, failedToSend);
    }

    @Test
    void valueOf() {
        assertThat(AuthenticationState.valueOf("UNKNOWN")).isEqualTo(unknown);
        assertThat(AuthenticationState.valueOf("WAITING_FOR_RESPONSE")).isEqualTo(waiting);
        assertThat(AuthenticationState.valueOf("INCORRECT_RESPONSE")).isEqualTo(incorrect);
        assertThat(AuthenticationState.valueOf("VERIFIED")).isEqualTo(verified);
        assertThat(AuthenticationState.valueOf("TOO_MANY_ATTEMPTS")).isEqualTo(tooManyAttempts);
        assertThat(AuthenticationState.valueOf("FAILED_TO_SEND")).isEqualTo(failedToSend);
    }
}
