package edu.northeastern.cs5500.starterbot.listener;

import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;
import edu.northeastern.cs5500.starterbot.config.command.SlashCommandConfig;
import edu.northeastern.cs5500.starterbot.controller.ButtonClickController;
import edu.northeastern.cs5500.starterbot.controller.UserEnterController;
import edu.northeastern.cs5500.starterbot.handler.button.ButtonHandler;
import edu.northeastern.cs5500.starterbot.handler.join.JoinHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * A generic EventListener that detects user interaction related to authentication services provided
 * by the bot. For example, listens to when a user has joined, button clicks, and slash commands,
 * etc.
 */
@IgnoreInGeneratedReport
@Slf4j
public class AuthenticationListener extends ListenerAdapter {
    @Inject Set<SlashCommandConfig> slashCommands;
    @Inject Set<ButtonHandler> buttonHandlers;
    @Inject Set<JoinHandler> guildMemberJoinHandlers;

    @Inject
    public AuthenticationListener() {
        super();
    }

    // when a new user joins the server
    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        for (JoinHandler guildMemberJoinHandler : guildMemberJoinHandlers) {
            guildMemberJoinHandler.handleGuildMemberJoinEvent(event);
        }
    }

    // when the user clicks on buttons
    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        var id = event.getButton().getId();
        var handlerName = id.split(":", 2)[0];
        for (ButtonHandler buttonHandler : buttonHandlers) {
            if (buttonHandler.getName().equals(handlerName)) {
                buttonHandler.handleButtonInteraction(event);
                return;
            }
        }
        log.error("Unknown button press: {}", id);
        event.reply("Sorry, I don't know how to handle that button.").queue();
    }

    // when the user uses slash commands
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        for (SlashCommandConfig slashCommand : slashCommands) {
            if (slashCommand.getName().equals(event.getName())) {
                slashCommand.handleSlashCommandInteraction(event);
                return;
            }
        }
        log.error("Unknown slash command: {}", event.getName());
        event.reply("Sorry, I don't know how to handle that command.").queue();
    }

    /*
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event)
    {
        if (event.getChannelType() != ChannelType.PRIVATE)
        {
            // We only listen to direct/private messages
            return;
        }

        for (AuthenticationHandler auth : authenticationMethods) {
            if(auth.messageReceived(event)) {
                return;
            }
        }

        event.getMessage().reply("No authentication session found!").queue();
    }
    */
}
