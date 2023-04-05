package edu.northeastern.cs5500.starterbot.listener;

import edu.northeastern.cs5500.starterbot.config.authentication.AuthenticationConfig;
import edu.northeastern.cs5500.starterbot.config.authentication.AuthenticationType;
import edu.northeastern.cs5500.starterbot.controller.ButtonClickController;
import edu.northeastern.cs5500.starterbot.controller.SlashCommandController;
import edu.northeastern.cs5500.starterbot.controller.UserEnterController;
import edu.northeastern.cs5500.starterbot.model.UserGuild;
import edu.northeastern.cs5500.starterbot.service.AuthenticationService;
import edu.northeastern.cs5500.starterbot.config.command.VerifySlashCommand;
import edu.northeastern.cs5500.starterbot.service.UserEnterService;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Slf4j
public class AuthenticationListener extends ListenerAdapter {
    private UserEnterController userEnterController;
    private ButtonClickController buttonClickController;
    private SlashCommandController slashCommandController;

    /*
    @Inject
    VerifySlashCommand verifySlashCommand;
    @Inject Map<AuthenticationType, AuthenticationConfig> authenticationMethods;
    @Inject
    AuthenticationService authenticationService;
    @Inject
    UserEnterService userEnterService;
     */

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
        log.info("On guild member join");
        userEnterController.handleUserEvent(event);
    }

    // when the user inputs an answer
    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        buttonClickController.onButtonInteraction(event);
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if (event.getName().equals("verify")) {
            slashCommandController.onSlashCommandInteraction(event);
        }
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
