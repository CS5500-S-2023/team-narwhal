package edu.northeastern.cs5500.starterbot.listener;

import edu.northeastern.cs5500.starterbot.authentication.AuthenticationHandler;
import edu.northeastern.cs5500.starterbot.controller.UserEnterController;

import java.util.*;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

@Slf4j
public class AuthenticationListener extends ListenerAdapter {
    private UserEnterController userEnterController;

    @Inject Set<AuthenticationHandler> authenticationMethods;

    @Inject
    public AuthenticationListener(UserEnterController userEnterController) {
        super();
        this.userEnterController = userEnterController;
    }

    // when a new user joins the server
    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        log.info("On guild member join");
        userEnterController.handlerUserEvent(event);
    }
    
    // when the user inputs an answer
    
    @Override
    public void onSlashCommand(@Nonnull SlashCommandEvent event){
        if (event.getName().equals("verify")) {
            // listen to user input with /verify
            String userInput = event.getCommandData
          }

    }

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

    
}
