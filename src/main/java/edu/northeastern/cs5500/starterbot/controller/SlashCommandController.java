package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.EventUserGuild;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import edu.northeastern.cs5500.starterbot.service.AuthenticationService;
import edu.northeastern.cs5500.starterbot.service.UserEnterService;
import edu.northeastern.cs5500.starterbot.view.BotView;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.PermissionOverrideAction;

@Slf4j
public class SlashCommandController {
  private AuthenticationService authenticationService;
  private UserEnterService userEnterService;
  private BotView view;

  @Inject
  GenericRepository<EventUserGuild> eventUserGuildRepository;

  @Inject
  public SlashCommandController(AuthenticationService authenticationService,
      UserEnterService userEnterService,
      BotView view) {
    this.view = view;
    this.authenticationService = authenticationService;
    this.userEnterService = userEnterService;
  }

  public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
    log.info("onSlashCommandInteraction: {}", event.getName());
    log.info(event.getUser().getId());
    try {
      String slashCommandType = event.getName();
      switch (slashCommandType) {
        case "verify":
          String userInput = Objects.requireNonNull(event.getOption("content")).getAsString();
          String verifiedStatusMsg = authenticationService.authenticateUser(event.getUser().getId(), userInput);
          event.reply(verifiedStatusMsg).queue();
          String guildId = getGuildIdForEventUser(event.getUser().getId());
          for (TextChannel channel: event.getJDA().getGuildById(guildId).getTextChannels()){
            Member member = event.getJDA().getGuildById(guildId).getMemberByTag(event.getUser().getAsTag());
            PermissionOverrideAction override = channel.upsertPermissionOverride(member);
            override.grant(Permission.VIEW_CHANNEL).queue();
          }

          // once user is verified, delete chat
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

  @Nonnull
  protected String getGuildIdForEventUser(String eventUserID) {
    for (EventUserGuild currentUserGuild : eventUserGuildRepository.getAll()) {
      if (eventUserID.equals(currentUserGuild.getUserEventId())) {
        return currentUserGuild.getGuildId();
      }
    }
    // throw custom user not found exceptions
    throw new RuntimeException("User not found!");
  }

}
