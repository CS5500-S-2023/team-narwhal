package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.service.AuthenticationService;
import edu.northeastern.cs5500.starterbot.view.BotView;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;
import nl.captcha.Captcha;

/**
 * Listens to various button click events and delegate to service classes to handle these events,
 * then updates the view.
 */
@Slf4j
public class ButtonClickController {

  private AuthenticationService authenticationService;
  private BotView view;

  @Inject
  public ButtonClickController(AuthenticationService authenticationService, BotView view) {
    this.authenticationService = authenticationService;
    this.view = view;
  }

    /**
     * Handles all types of button click events supported by the program.
     * @param event a ButtonInteractionEvent object
     */
  public void handleButtonInteraction(@Nonnull ButtonInteractionEvent event) {
    log.info("onButtonInteraction: {}", event.getButton().getId());
    try {
      String id = event.getButton().getId();
      String buttonCategory = id.split(":", 2)[0];
      switch (buttonCategory) {
        case "authenticate":
          String authenticationType = id.split(":", 2)[1];
          handleAuthenticationButtonClicks(event, authenticationType);
          break;
        default:
          // TODO add custom exception
          throw new IllegalStateException(event.getButton().getId());
      }
    } catch (Exception e) {
      // TODO
      log.error("Unknown button handler!");
    }
  }

    /**
     * Helper to handle the authentication button clicks.
     * @param event a ButtonInteractionEvent object
     * @param authenticationType the type of authentication as a String
     */
  private void handleAuthenticationButtonClicks(@Nonnull ButtonInteractionEvent event,
      String authenticationType) {
    switch (authenticationType) {
      case "captcha":
        boolean isNull = (event.getMember() == null);
        log.info("The member is null: " + isNull);
        MessageChannelUnion channel = event.getChannel();
        event.reply("Generating captcha...").queue();
        String userId = event.getUser().getId();
        // generateCaptcha handles adding the Authentication challenge to db
        Captcha captcha = authenticationService.generateCaptcha(userId);
        FileUpload captchaFile = view.generateCaptchaView(captcha);
        event.getUser().openPrivateChannel()
            .flatMap(channel -> channel.sendFiles(captchaFile)).queue();

        break;

      case "text":

        break;

      case "email":

        break;

      default:
        // TODO add custom exception
        throw new IllegalStateException(event.getButton().getId());
    }
  }
}
