package edu.northeastern.cs5500.starterbot.listener;

import edu.northeastern.cs5500.starterbot.controller.ButtonClickController;
import edu.northeastern.cs5500.starterbot.controller.SlashCommandController;
import edu.northeastern.cs5500.starterbot.controller.UserEnterController;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * A generic EventListener that detects user interaction related to authentication services provided
 * by the bot. For example, listens to when a user has joined, button clicks, and slash commands,
 * etc.
 */
@Slf4j
public class AuthenticationListener extends ListenerAdapter {
    // TODO: can this code be replaced with lombok?
    private UserEnterController userEnterController;
    private ButtonClickController buttonClickController;
    private SlashCommandController slashCommandController;

    // constructor with supported event interactions
    @Inject
    public AuthenticationListener(
            UserEnterController userEnterController, ButtonClickController buttonClickController,
        SlashCommandController slashCommandController) {
        super();
        this.userEnterController = userEnterController;
        this.buttonClickController = buttonClickController;
        this.slashCommandController = slashCommandController;
    }

    // when a new user joins the server
    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        userEnterController.handleUserJoinEvent(event);
    }

    // when the user clicks on buttons
    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        buttonClickController.onButtonInteraction(event);
    }

    // when the user uses slash commands
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if (event.getName().equals("verify")) {
            slashCommandController.onSlashCommandInteraction(event);
        }
        // other types of slash command
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
