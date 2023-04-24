package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.AuthenticationChallenge;
import edu.northeastern.cs5500.starterbot.model.AuthenticationState;
import edu.northeastern.cs5500.starterbot.repository.ChallengeRepository;
import java.util.Date;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Handles challenges and their business logic: 1. Create a challenge 2. Update a challenge 3.
 * Delete a challenge 4. Update attempts
 */
@Singleton
public class ChallengeController {
    ChallengeRepository challengeRepository;

    @Inject
    public ChallengeController(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    /**
     * Creates an AuthenticationChallege.
     *
     * @param userId - The user creating the AuthenticationChallenge.
     * @param answer - The answer to the AuthenticationChallenge.
     * @return an AuthenticationChallenge.
     */
    public AuthenticationChallenge createChallenge(String userId, String answer) {
        return AuthenticationChallenge.builder().eventUserId(userId).answer(answer).build();
    }

    /**
     * Gets the correct AuthenticationChallenge for the user from the ChallengeRepository.
     *
     * @param userId - The user whose AuthenticationChallenge we are getting.
     * @return an AuthenticationChallenge.
     */
    @Nullable
    public AuthenticationChallenge getChallenge(String userId) {
        return challengeRepository.get(userId);
    }

    /**
     * Adds an AuthenticationChallenge to the ChallengeRepository.
     *
     * @param challenge - The AuthenticationChallenge we are adding to the ChallengeRepository.
     */
    public void addChallenge(@Nonnull AuthenticationChallenge challenge) {
        challengeRepository.add(challenge);
    }

    /**
     * Removes an AuthenticationChallenge from the ChallengeRepository.
     *
     * @param userId - The user whose AuthenticationChallenge we are removing from the repository.
     */
    public void removeChallenge(String userId) {
        AuthenticationChallenge challenge = challengeRepository.get(userId);
        challengeRepository.delete(Objects.requireNonNull(challenge.getId()));
    }

    /**
     * Restarts the AuthenticationChallenge.
     *
     * @param challenge - The AuthenticationChallenge we are restarting.
     * @param answer - The updated answer to the AuthenticationChallenge.
     * @return an updated AuthenticationChallenge.
     */
    public AuthenticationChallenge restartChallenge(
            AuthenticationChallenge challenge, String answer) {
        challenge.setAnswer(answer);
        challenge.setState(AuthenticationState.UNKNOWN);

        return challenge;
    }

    /**
     * Updates the AuthenticationState to VERIFIED.
     *
     * @param challenge - The AuthenticationChallenge we are updating.
     * @return an updated AuthenticationChallenge.
     */
    public AuthenticationChallenge passChallenge(@Nonnull AuthenticationChallenge challenge) {
        challenge.setState(AuthenticationState.VERIFIED);
        return challenge;
    }

    /**
     * Updates the AuthenticationState to INCORRECT_RESPONSE.
     *
     * @param challenge - The AuthenticationChallenge we are updating.
     * @return an updated AuthenticationChallenge.
     */
    public AuthenticationChallenge failChallenge(@Nonnull AuthenticationChallenge challenge) {
        int attempts = challenge.getNumAttempts();
        challenge.setNumAttempts(attempts + 1);
        challenge.setState(AuthenticationState.INCORRECT_RESPONSE);

        return challenge;
    }

    // TODO: Future implementation - Use method for timeout & lockout
    public boolean checkCooldown(AuthenticationChallenge challenge) {
        return new Date().after(challenge.getTimeStamp());
    }
}
