package edu.northeastern.cs5500.starterbot.controller;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.AuthenticationChallenge;
import edu.northeastern.cs5500.starterbot.model.AuthenticationState;
import edu.northeastern.cs5500.starterbot.repository.ChallengeRepository;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChallengeControllerTest {

    private ChallengeRepository challengeRepository;
    private ChallengeController challengeController;
    private AuthenticationChallenge challenge1;
    private AuthenticationChallenge challenge2;

    @BeforeEach
    void setUp() {
        challengeRepository = new ChallengeRepository();
        challengeController = new ChallengeController(challengeRepository);
        challenge1 =
                AuthenticationChallenge.builder().eventUserId("user1").answer("answer").build();
        challenge2 =
                AuthenticationChallenge.builder().eventUserId("user2").answer("answer2").build();
        challengeController.addChallenge(challenge1);
    }

    @Test
    void testCreateChallenge() {
        challengeController.createChallenge("user1", "correct");
        assertThat(challengeRepository.get("user1").getAnswer()).isEqualTo("answer");
    }

    @Test
    void testGetChallenge() {
        assertThat(Objects.requireNonNull(challengeController.getChallenge("user1")).getAnswer())
                .isEqualTo("answer");
    }

    @Test
    void testAddChallenge() {
        challengeController.addChallenge(challenge2);
        assertThat(challengeRepository.get("user2").getAnswer()).isEqualTo("answer2");
    }

    @Test
    void testRemoveChallenge() {
        challengeController.removeChallenge("user1");
        assertThat(challengeRepository.get("user1")).isNull();
    }

    @Test
    void testRestartChallenge() {
        challengeController.restartChallenge(challenge1, "answer3");
        assertThat(challengeRepository.get("user1").getAnswer()).isEqualTo("answer3");
    }

    @Test
    void testPassChallenge() {
        challengeController.passChallenge(challenge2);
        assertThat(challenge2.getState()).isEqualTo(AuthenticationState.VERIFIED);
    }

    @Test
    void testFailChallenge() {
        challengeController.failChallenge(challenge2);
        assertThat(challenge2.getState()).isEqualTo(AuthenticationState.INCORRECT_RESPONSE);
    }

    @Test
    void testCheckCooldown() {
        assertThat(challengeController.checkCooldown(challenge1)).isTrue();
    }
}
