package edu.northeastern.cs5500.starterbot.service;

import edu.northeastern.cs5500.starterbot.model.AuthenticationChallenge;
import edu.northeastern.cs5500.starterbot.model.AuthenticationState;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import nl.captcha.Captcha;

/** Authenticate users. */
@Slf4j
public class AuthenticationService {

    @Inject GenericRepository<AuthenticationChallenge> challengeRepository;

    @Inject
    AuthenticationService() {}

    /**
     * Generate a captcha and save it as a new AuthenticationChallenge for this user in the data
     * repo.
     *
     * @param eventUserId the user that the AuthenticationChallenge is associated with, use the
     *     userId from the event
     * @return a Captcha object
     */
    public Captcha generateCaptcha(String eventUserId) {
        Captcha captcha = new Captcha.Builder(200, 50).addText().build();

        // add the generated AuthenticationChallenge to in memory db
        // TODO: the problem is that, when the bot shuts down and the user rejoins, they are
        // assigned a new event user ID?
        // find out if this is true;
        AuthenticationChallenge data =
                AuthenticationChallenge.builder()
                        .eventUserId(eventUserId)
                        .answer(captcha.getAnswer())
                        .build();
        challengeRepository.add(data);
        return captcha;
    }

    /**
     * Authenticate a user and output a message based on the authentication status.
     *
     * @param eventUserId the userId as a String
     * @param userInput the user input as a String
     * @return a Boolean indicating the authentication status
     */
    public boolean authenticateUser(String eventUserId, String userInput) {
        // TODO: based on the message output from this, the controller needs to do things
        // could also change the return type to the verification status, easier for controller
        // to work with

        // TODO: take care of too many attempts
        AuthenticationChallenge captchaModel = getAuthenticationModelForMemberId(eventUserId);
        if (captchaModel.getAnswer().equals(userInput)) {
            // update the model
            captchaModel.setState(AuthenticationState.VERIFIED);
            captchaModel.setNumAttempts(captchaModel.getNumAttempts() + 1);
            // TODO: take care of timeout
            return true;
        }

        captchaModel.setState(AuthenticationState.INCORRECT_RESPONSE);
        // increase attempt count
        captchaModel.setNumAttempts(captchaModel.getNumAttempts() + 1);
        // send a msg to retry
        return false;
    }

    /**
     * Get the authentication challenge associated with this user
     *
     * @param eventUserID the userId
     * @return an AuthenticationChallenge object
     */
    @Nonnull
    protected AuthenticationChallenge getAuthenticationModelForMemberId(String eventUserID) {
        for (AuthenticationChallenge currentUserAuth : challengeRepository.getAll()) {
            if (currentUserAuth.getEventUserId().equals(eventUserID)) {
                return currentUserAuth;
            }
        }
        // throw custom user not found exceptions
        throw new RuntimeException("User not found!");
    }
}
