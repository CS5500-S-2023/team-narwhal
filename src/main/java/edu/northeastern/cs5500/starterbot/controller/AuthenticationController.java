package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.exceptions.FailedToChangeUserRoleException;
import edu.northeastern.cs5500.starterbot.exceptions.NoAuthenticationSessionException;
import edu.northeastern.cs5500.starterbot.model.AuthenticationChallenge;
import edu.northeastern.cs5500.starterbot.model.Membership;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

/**
 * A class that handles authentication flow: 1. Starts a challenge and stores it in a repository 2.
 * Handles user attempts and determines if it's successful or not
 */
@Singleton
public class AuthenticationController {
    RoleController roleController;
    ChallengeController challengeController;
    MembershipController membershipController;

    @Inject
    public AuthenticationController(
            RoleController roleController,
            ChallengeController challengeController,
            MembershipController membershipController) {
        this.roleController = roleController;
        this.challengeController = challengeController;
        this.membershipController = membershipController;
    }

    /**
     * Method to start the AuthenticationChallenge.
     *
     * @param userId - The ID for the user starting the AuthenticationChallege.
     * @param answer - The answer to the AuthenticationChallenge.
     * @return the updated AuthenticationChallenge.
     */
    public AuthenticationChallenge startChallenge(@Nonnull String userId, String answer) {
        AuthenticationChallenge challenge = challengeController.getChallenge(userId);

        if (challenge != null) {
            challenge = challengeController.restartChallenge(challenge, answer);
        } else {
            challenge = challengeController.createChallenge(userId, answer);
        }

        challengeController.addChallenge(Objects.requireNonNull(challenge));
        return challenge;
    }

    /**
     * Method to complete the AuthenticationChallenge.
     *
     * @param userId - The ID for the user completing the AuthenticationChallege.
     */
    public void completeChallenge(@Nonnull String userId) {
        challengeController.removeChallenge(userId);
        membershipController.removeMembership(userId);
    }

    /**
     * Method to attempt the AuthenticationChallenge.
     *
     * @param userId - The ID for the user attempting the AuthenticationChallege.
     * @param userInput - The user's answer to the AuthenticationChallenge.
     * @return the updated AuthenticationChallenge.
     * @throws NoAuthenticationSessionException
     * @throws FailedToChangeUserRoleException
     */
    public AuthenticationChallenge attemptChallenge(@Nonnull String userId, String userInput)
            throws NoAuthenticationSessionException, FailedToChangeUserRoleException {
        AuthenticationChallenge challenge = challengeController.getChallenge(userId);

        if (challenge == null) {
            throw new NoAuthenticationSessionException(
                    "No authentication session to attempt for user.");
        }

        if (challenge.getAnswer().equals(userInput)) {
            return onSuccessfulAttempt(userId, challenge);
        } else {
            return challengeController.failChallenge(challenge);
        }
    }

    /**
     * Method to update AuthenticationChallenge when an attempt succeeds.
     *
     * @param userId - The ID for the user that passed the AuthenticationChallege.
     * @param challenge - The AuthenticationChallenge to update.
     * @return the updated AuthenticationChallenge.
     * @throws FailedToChangeUserRoleException
     */
    public AuthenticationChallenge onSuccessfulAttempt(
            @Nonnull String userId, @Nonnull AuthenticationChallenge challenge)
            throws FailedToChangeUserRoleException {
        Membership membership = membershipController.getMembership(userId);

        if (Objects.isNull(membership)) {
            return null;
        }

        User user = Objects.requireNonNull(membership).getUser();
        Guild guild = membership.getGuild();

        if (Objects.nonNull(guild) && Objects.nonNull(user)) {
            roleController.addVerifiedRoleToUser(guild, user);
        }

        completeChallenge(userId);
        return challengeController.passChallenge(challenge);
    }
}
