package edu.northeastern.cs5500.starterbot.service;

import edu.northeastern.cs5500.starterbot.model.AuthenticationChallenge;
import edu.northeastern.cs5500.starterbot.model.AuthenticationState;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import edu.northeastern.cs5500.starterbot.config.command.VerifySlashCommand;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import nl.captcha.Captcha;
/**
 * Authenticate users.
 */
@Slf4j
public class AuthenticationService {
    @Inject
    GenericRepository<AuthenticationChallenge> challengeRepository;

    @Inject
    AuthenticationService() {
    }

    // generate captcha, save it in the model, and present the captcha to user
    public Captcha generateCaptcha(@Nonnull ButtonInteractionEvent event) {
        Captcha captcha = new Captcha.Builder(200, 50).addText().build();
        AuthenticationChallenge data =
                new AuthenticationChallenge(event.getUser().getId(), captcha.getAnswer());
        challengeRepository.add(data);
        return captcha;
    }

    public String authenticateUser (String userId, String userInput) {
        log.info("event: /verify");
        AuthenticationChallenge captchaModel = getAuthenticationModelForMemberId(userId);
        if (captchaModel.getAnswer().equals(userInput)) {
            // update the model
            captchaModel.setState(AuthenticationState.VERIFIED);
            captchaModel.setNumAttempts(captchaModel.getNumAttempts() + 1);
            // TODO: take care of timeout
            // give message
            return "You are verified!";

            // change user privilege
        } else {
            // increase attempt count
            // send a msg to retry
            return "You've entered the wrong answer, please try again.";
            // represent captcha
        }
    }

    @Nonnull
    protected AuthenticationChallenge getAuthenticationModelForMemberId(String discordMemberId) {
        for (AuthenticationChallenge currentUserAuth : challengeRepository.getAll()) {
            if (currentUserAuth.getDiscordMemberId().equals(discordMemberId)) {
                return currentUserAuth;
            }
        }
        // throw custom user not found exceptions
        throw new RuntimeException("User not found!");
    }
}
