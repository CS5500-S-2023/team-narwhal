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

    public AuthenticationChallenge createChallenge(String userId, String answer) {
        return AuthenticationChallenge.builder().eventUserId(userId).answer(answer).build();
    }

    @Nullable
    public AuthenticationChallenge getChallenge(String userId) {
        return challengeRepository.get(userId);
    }

    public void addChallenge(@Nonnull AuthenticationChallenge challenge) {
        challengeRepository.add(challenge);
    }

    public void removeChallenge(String userId) {
        AuthenticationChallenge challenge = challengeRepository.get(userId);
        challengeRepository.delete(Objects.requireNonNull(challenge.getId()));
    }

    public AuthenticationChallenge restartChallenge(
            AuthenticationChallenge challenge, String answer) {
        challenge.setAnswer(answer);
        challenge.setState(AuthenticationState.UNKNOWN);

        return challenge;
    }

    public AuthenticationChallenge passChallenge(@Nonnull AuthenticationChallenge challenge) {
        challenge.setState(AuthenticationState.VERIFIED);
        return challenge;
    }

    public AuthenticationChallenge failChallenge(@Nonnull AuthenticationChallenge challenge) {
        int attempts = challenge.getNumAttempts();
        challenge.setNumAttempts(attempts + 1);
        challenge.setState(AuthenticationState.INCORRECT_RESPONSE);

        return challenge;
    }

    public boolean checkCooldown(AuthenticationChallenge challenge) {
        return new Date().after(challenge.getTimeStamp());
    }
}
