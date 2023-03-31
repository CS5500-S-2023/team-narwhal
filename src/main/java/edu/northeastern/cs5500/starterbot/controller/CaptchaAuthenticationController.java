package edu.northeastern.cs5500.starterbot.controller;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import edu.northeastern.cs5500.starterbot.model.CaptchaAuthenticationModel;
import edu.northeastern.cs5500.starterbot.listener.BotView;
import edu.northeastern.cs5500.starterbot.model.authenticationChallenge.AuthenticationChallenge;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import nl.captcha.Captcha;

public class CaptchaAuthenticationController implements Authenticator {
    private CaptchaAuthenticationModel model;
    private BotView view;

    public CaptchaAuthenticationController (CaptchaAuthenticationModel model, BotView view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
    }

    //@Inject GenericRepository<CaptchaAuthenticationChallenge> challengeRepository;

    @Inject
    CaptchaAuthenticationController() {
        // needed for Dagger
    }

    /*
    @Nullable
    AuthenticationChallenge getChallenge(String discordMemberId) {
        for (CaptchaAuthenticationChallenge challenge : challengeRepository.getAll()) {
            if (discordMemberId.equals(challenge.getDiscordMemberId())) {
                return challenge;
            }
        }
        return null;
    }
    */


    public AuthenticationChallenge begin(@Nonnull ButtonInteractionEvent event) {
        String discordMemberId = event.getMember().getUser().getId();

        // calls the model to generate a captcha
         Captcha captcha = model.generateCaptcha(discordMemberId);

        // tells the view to render captcha
        view.renderCaptcha(event, captcha);
        throw new UnsupportedOperationException("Unimplemented method 'begin'");
    }

    /* 
    @Override
    public boolean verify(String discordMemberId, String inputCode)
            throws TooManyAttemptsException, NoAuthenticationSessionException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verify'");
    }
    */
    
}
