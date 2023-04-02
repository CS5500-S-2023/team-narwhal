package edu.northeastern.cs5500.starterbot.controller;

import javax.annotation.Nullable;
import javax.inject.Inject;

import edu.northeastern.cs5500.starterbot.authentication.NoAuthenticationSessionException;
import edu.northeastern.cs5500.starterbot.authentication.TooManyAttemptsException;
import edu.northeastern.cs5500.starterbot.model.AuthenticationChallenge;
import edu.northeastern.cs5500.starterbot.model.CaptchaAuthenticationChallenge;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;

public class CaptchaAuthenticationController {

    //@Inject GenericRepository<CaptchaAuthenticationChallenge> challengeRepository;
    private View view;

    @Inject
    CaptchaAuthenticatorController() {
        // needed for Dagger
    }

    @Nullable
    AuthenticationChallenge getChallenge(String discordMemberId) {
        for (CaptchaAuthenticationChallenge challenge : challengeRepository.getAll()) {
            if (discordMemberId.equals(challenge.getDiscordMemberId())) {
                return challenge;
            }
        }
    
        return null;
    }



    void privideAuthentication (@Nonnull ButtonInteractionEvent event) {
        Captcha captcha = new Captcha.Builder(200, 50).addText().build();
        

        throw new UnsupportedOperationException("Unimplemented method 'begin'");
    }

    @Override
    public boolean verify(String discordMemberId, String inputCode)
            throws TooManyAttemptsException, NoAuthenticationSessionException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verify'");
    }
    
}
