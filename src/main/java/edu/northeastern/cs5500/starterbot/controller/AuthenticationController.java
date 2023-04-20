package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.exceptions.FailedToChangeUserRoleException;
import edu.northeastern.cs5500.starterbot.exceptions.NoAuthenticationSessionException;
import edu.northeastern.cs5500.starterbot.model.AuthenticationChallenge;
import edu.northeastern.cs5500.starterbot.model.Membership;
import edu.northeastern.cs5500.starterbot.service.JDAService;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

/**
 * Handles authentication: 1. Starts a challenge and stores it in a repository 2. Handles user
 * attempts and determines if it's successful or not
 */
@Singleton
public class AuthenticationController {
    JDAService jda;
    RoleController roleController;
    ChallengeController challengeController;
    MembershipController membershipController;

    @Inject
    public AuthenticationController(
            JDAService jda,
            RoleController roleController,
            ChallengeController challengeController,
            MembershipController membershipController) {
        this.jda = jda;
        this.roleController = roleController;
        this.challengeController = challengeController;
        this.membershipController = membershipController;
    }

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

    public void completeChallenge(@Nonnull String userId) {
        challengeController.removeChallenge(userId);
        membershipController.removeMembership(userId);
    }

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
            roleController.removeUnverifiedRoleFromUser(guild, user);
        }

        completeChallenge(userId);
        return challengeController.passChallenge(challenge);
    }
}
