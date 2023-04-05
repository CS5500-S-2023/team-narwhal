package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.service.AuthenticationService;
import edu.northeastern.cs5500.starterbot.view.BotView;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;
import nl.captcha.Captcha;

@Slf4j
public class ButtonClickController {
    private AuthenticationService authenticationService;
    private BotView view;

    @Inject
    public ButtonClickController(AuthenticationService authenticationService, BotView view) {
        this.authenticationService = authenticationService;
        this.view = view;
    }

    // entry point, route to different authentication methods and other user actions
    // all button clicks
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        log.info("onButtonInteraction: {}", event.getButton().getId());
        try {
            String id = event.getButton().getId();
            Objects.requireNonNull(id);
            // String handlerCategory = id.split(":", 2)[0];
            // e.g. authenticate, use if we add other buttons
            String handlerType = id.split(":", 2)[1];

            // could have another switch statement to handle other handler types
            switch (handlerType) {
                case "captcha":
                    //long startTime = System.currentTimeMillis();
                    event.reply("Generating captcha...").queue();
                    Captcha captcha = authenticationService.generateCaptcha(event);
                    // TODO: add new captcha challenge to the db
                    FileUpload captchaFile = view.generateCaptchaView(captcha);
                    //System.out.println(System.currentTimeMillis() - startTime);
                    User member = event.getUser();
                    // reply on has 3 secs time
                    member.openPrivateChannel()
                        .flatMap(channel -> channel.sendFiles(captchaFile)).queue();
                    break;
                case "text":

                    break;
                case "email":

                    break;
                default:
                    throw new IllegalStateException(event.getButton().getId());
            }
        } catch (Exception e) {
            // catch then log
            // log.error("Unknown button handler: {}", handlerType);
            //e.printStackTrace();
            log.error("Unknown button handler!");
        }
    }
}
