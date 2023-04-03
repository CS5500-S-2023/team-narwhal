package edu.northeastern.cs5500.starterbot.controller;

import javax.annotation.Nullable;
import javax.inject.Inject;

import edu.northeastern.cs5500.starterbot.authentication.NoAuthenticationSessionException;
import edu.northeastern.cs5500.starterbot.authentication.TooManyAttemptsException;
import edu.northeastern.cs5500.starterbot.model.AuthenticationChallenge;
import edu.northeastern.cs5500.starterbot.model.CaptchaAuthenticationChallenge;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import edu.northeastern.cs5500.starterbot.view.BotView;

public class CaptchaAuthenticationController {

    @Inject GenericRepository<AuthenticationChallenge> challengeRepository;
    private BotView view;
    
    @Inject
    CaptchaAuthenticatorController(BotView view) {
        this.view = view;
    }

    // @Nullable
    // AuthenticationChallenge getChallenge(String discordMemberId) {
    //     for (CaptchaAuthenticationChallenge challenge : challengeRepository.getAll()) {
    //         if (discordMemberId.equals(challenge.getDiscordMemberId())) {
    //             return challenge;
    //         }
    //     }
    
    //     return null;
    // }

    // generate captcha, save it in the model, and present the captcha to user
    void generateCaptcha (@Nonnull ButtonInteractionEvent event) {
        Captcha captcha = new Captcha.Builder(200, 50).addText().build();
        AuthenticationChallenge model = new AuthenticationChallenge(event.getDiscordMemberId(), captcha.getAnswer());
        FileUpload captchaFile = view.generateCaptchaView(captcha);
        event.replyFiles(captchaFile).queue();
    }

    @Override
    public boolean verify(String discordMemberId, String inputCode)
            throws TooManyAttemptsException, NoAuthenticationSessionException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verify'");
    }
    
}
