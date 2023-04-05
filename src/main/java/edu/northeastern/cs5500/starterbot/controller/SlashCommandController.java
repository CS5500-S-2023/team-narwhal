package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.service.AuthenticationService;
import edu.northeastern.cs5500.starterbot.view.BotView;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;
import nl.captcha.Captcha;

@Slf4j
public class SlashCommandController {
  private AuthenticationService authenticationService;
  private BotView view;

  @Inject
  public SlashCommandController(AuthenticationService authenticationService, BotView view) {
    this.authenticationService = authenticationService;
    this.view = view;
  }

  public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
    log.info("onSlashCommandInteraction: {}", event.getName());
    try {
      String slashCommandType = event.getName();
      switch (slashCommandType) {
        case "verify":
          String userInput = Objects.requireNonNull(event.getOption("content")).getAsString();
          String verifiedStatusMsg = authenticationService.authenticateUser(event.getUser().getId(), userInput);
          event.reply(verifiedStatusMsg).queue();
          break;
        default:
          // throw custom exceptions
          throw new RuntimeException("Command not supported!");
      }
    } catch (Exception e) {
      e.printStackTrace();
      log.error("Unknown slash command!");
    }
  }


}
