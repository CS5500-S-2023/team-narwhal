package edu.northeastern.cs5500.starterbot.config.command;

import edu.northeastern.cs5500.starterbot.model.AuthenticationChallenge;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import edu.northeastern.cs5500.starterbot.service.AuthenticationService;
import edu.northeastern.cs5500.starterbot.service.UserPermissionUpdateService;
import edu.northeastern.cs5500.starterbot.view.BotView;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

@Singleton
@Slf4j
public class VerifySlashCommand implements SlashCommandConfig {

    @Inject BotView view;
    @Inject AuthenticationService authenticationService;
    @Inject UserPermissionUpdateService userPermissionUpdateService;

    @Inject
    public VerifySlashCommand() {
        // Defined public and empty for Dagger injection
    }

    @Override
    @Nonnull
    public String getName() {
        return "verify";
    }

    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Tell the bot your answer to this captcha")
                .addOption(
                        OptionType.STRING,
                        "answer",
                        "The bot will use this input to authenticate",
                        true);
    }

    @Override
    public void handleSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("User " + event.getUser().getId() + " is verifying");
        // 1. verify user answer
        // get the user input from the event
        String userInput = Objects.requireNonNull(event.getOption("answer")).getAsString();
        // verify user and return a message that indicates their verified status
        boolean verifiedStatus =
                authenticationService.authenticateUser(event.getUser().getId(), userInput);

        if (verifiedStatus) {
            event.reply("You are verified!").queue();
            // 2. update user permission
            // get the guildId using the event userId
            String guildId =
                    userPermissionUpdateService.getGuildIdForEventUser(event.getUser().getId());
            List<TextChannel> textChannelList =
                    Objects.requireNonNull(event.getJDA().getGuildById(guildId)).getTextChannels();
            // update user permission in text channels
            for (TextChannel channel : textChannelList) {
                // user tag is the username, use this to get the member in the guild/server
                String eventUserTag = event.getUser().getAsTag();
                Member member =
                        Objects.requireNonNull(event.getJDA().getGuildById(guildId))
                                .getMemberByTag(eventUserTag);
                userPermissionUpdateService
                        .grantUserPermissionInTextChannel(channel, member, Permission.VIEW_CHANNEL)
                        .queue();
            }
        } else {
            event.reply("You've entered the wrong answer, please try again.").queue();
        }
    }

    
}
