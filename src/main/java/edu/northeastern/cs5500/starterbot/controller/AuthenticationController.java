package edu.northeastern.cs5500.starterbot.controller;

import javax.annotation.Nullable;
import javax.inject.Inject;

import edu.northeastern.cs5500.starterbot.authentication.NoAuthenticationSessionException;
import edu.northeastern.cs5500.starterbot.authentication.TooManyAttemptsException;
import edu.northeastern.cs5500.starterbot.command.VerifyCommand;
import edu.northeastern.cs5500.starterbot.model.AuthenticationChallenge;
import edu.northeastern.cs5500.starterbot.model.CaptchaAuthenticationChallenge;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import edu.northeastern.cs5500.starterbot.view.BotView;

public class AuthenticationController {

    @Inject VerifyCommand verifyCommand;
    private BotView view;
    
    @Inject
    AuthenticatorController(BotView view) {
        this.view = view;
    }

    // generate captcha, save it in the model, and present the captcha to user
    public void generateCaptcha (@Nonnull ButtonInteractionEvent event) {
        Captcha captcha = new Captcha.Builder(200, 50).addText().build();
        AuthenticationChallenge model = new AuthenticationChallenge(event.getDiscordMemberId(), captcha.getAnswer());
        FileUpload captchaFile = view.generateCaptchaView(captcha);
        event.replyFiles(captchaFile).queue();
    }    
}
