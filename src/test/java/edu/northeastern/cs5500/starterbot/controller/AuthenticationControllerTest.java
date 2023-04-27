package edu.northeastern.cs5500.starterbot.controller;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.exceptions.FailedToChangeUserRoleException;
import edu.northeastern.cs5500.starterbot.exceptions.NoAuthenticationSessionException;
import edu.northeastern.cs5500.starterbot.exceptions.NoMembershipFoundException;
import edu.northeastern.cs5500.starterbot.model.AuthenticationChallenge;
import edu.northeastern.cs5500.starterbot.model.AuthenticationState;
import edu.northeastern.cs5500.starterbot.repository.ChallengeRepository;
import edu.northeastern.cs5500.starterbot.repository.MembershipRepository;
import javax.annotation.Nonnull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthenticationControllerTest {
    AuthenticationController testAuthenticationController;
    RoleController roleController;
    ChallengeController challengeController;
    MembershipController membershipController;

    @Nonnull String testUserId = "user";
    @Nonnull String testAnswer = "answer";

    @BeforeEach
    void setUp() {
        roleController = new RoleController();
        challengeController = new ChallengeController(new ChallengeRepository());
        membershipController = new MembershipController(new MembershipRepository());

        testAuthenticationController =
                new AuthenticationController(
                        roleController, challengeController, membershipController);
    }

    @Test
    void testStartChallengeWithNewUser() {
        assertThat(challengeController.getChallenge(testUserId)).isNull();
        testAuthenticationController.startChallenge(testUserId, testAnswer);
        assertThat(challengeController.getChallenge(testUserId)).isNotNull();
    }

    @Test
    void testStartChallengeWithExistingChallenge() {
        String newAnswer = "new";
        AuthenticationChallenge expectedChallenge =
                challengeController.createChallenge(testUserId, testAnswer);
        challengeController.addChallenge(expectedChallenge);
        AuthenticationChallenge actualChallenge =
                testAuthenticationController.startChallenge(testUserId, newAnswer);

        assertThat(actualChallenge.getId()).isEqualTo(expectedChallenge.getId());
        assertThat(actualChallenge.getAnswer()).isEqualTo(newAnswer);
    }

    @Test
    void testCompleteChallengeRemovesChallenge() {
        testAuthenticationController.startChallenge(testUserId, testAnswer);
        assertThat(challengeController.getChallenge(testUserId)).isNotNull();
        testAuthenticationController.completeChallenge(testUserId);
        assertThat(challengeController.getChallenge(testUserId)).isNull();
    }

    @Test
    void testAttemptChallengeWithCorrectAnswer()
            throws NoAuthenticationSessionException, FailedToChangeUserRoleException,
                    NoMembershipFoundException {
        // skipping testing with non-empty MembershipRepository since it depends on JDA objects
        testAuthenticationController.startChallenge(testUserId, testAnswer);

        Assertions.assertThrows(
                NoMembershipFoundException.class,
                () -> {
                    testAuthenticationController.attemptChallenge(testUserId, testAnswer);
                });
    }

    @Test
    void testAttemptChallengeWithWrongAnswer()
            throws NoAuthenticationSessionException, FailedToChangeUserRoleException,
                    NoMembershipFoundException {
        testAuthenticationController.startChallenge(testUserId, testAnswer);
        AuthenticationChallenge testChallenge =
                testAuthenticationController.attemptChallenge(testUserId, "wrong answer");

        assertThat(testChallenge.getState()).isEqualTo(AuthenticationState.INCORRECT_RESPONSE);
    }

    @Test
    void testAttemptChallengeForNonexistingUser() {
        Assertions.assertThrows(
                NoAuthenticationSessionException.class,
                () -> {
                    testAuthenticationController.attemptChallenge("newUser", testAnswer);
                });
    }

    @Test
    void testOnSuccessfulAttempt() {
        // skipping other test variations for this method since they depend on JDA objects
        Assertions.assertThrows(
                NoMembershipFoundException.class,
                () -> {
                    AuthenticationChallenge testChallenge =
                            testAuthenticationController.startChallenge(testUserId, testAnswer);
                    testAuthenticationController.onSuccessfulAttempt(testUserId, testChallenge);
                });
    }
}
