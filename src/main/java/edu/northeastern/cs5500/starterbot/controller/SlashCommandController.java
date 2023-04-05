package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.AuthenticationChallenge;
import edu.northeastern.cs5500.starterbot.model.GuildStore;
import edu.northeastern.cs5500.starterbot.service.AuthenticationService;
import edu.northeastern.cs5500.starterbot.service.UserEnterService;
import edu.northeastern.cs5500.starterbot.view.BotView;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.PermissionOverrideAction;
import net.dv8tion.jda.api.utils.FileUpload;
import nl.captcha.Captcha;

@Slf4j
public class SlashCommandController {
  private AuthenticationService authenticationService;
  private UserEnterService userEnterService;
  private BotView view;
  private GuildStore guildStore;

  @Inject
  public SlashCommandController(AuthenticationService authenticationService,
      UserEnterService userEnterService,
      BotView view,
      GuildStore guildStore) {
    this.authenticationService = authenticationService;
    this.userEnterService = userEnterService;
    this.view = view;
    this.guildStore = guildStore;
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
          for (TextChannel channel: guildStore.getGuild().getTextChannels()){
            //System.out.println(guildChannel == null);
            //System.out.println(guildChannel.getPermissionContainer() == null);
            Member member = guildStore.getGuild().getMemberByTag(event.getUser().getAsTag());
            //System.out.println("Member is: " + (member == null));
            PermissionOverrideAction override = channel.upsertPermissionOverride(member);
            System.out.println(override == null);
            override.grant(Permission.VIEW_CHANNEL).queue();
            //override.getManager().grant(Permission.VIEW_CHANNEL).queue();
          }

          //guildStore.getGuild().addMember(new ProcessBuilder().environment().get("BOT_TOKEN"), event.getUser()).queue();
//          for (GuildChannel guildChannel: guildStore.getGuild().getChannels()){
//            // guildStore.getGuild().getMember(event.getUser())
//            // event.getGuild().getMember(event.getUser())
//            PermissionOverride override = guildChannel.getPermissionContainer()
//                .getPermissionOverride(guildChannel.getGuild().getMember(event.getUser()));
//            override.getManager().grant(Permission.VIEW_CHANNEL).queue();
//          }
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
}
