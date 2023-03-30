package edu.northeastern.cs5500.starterbot.listener;

import edu.northeastern.cs5500.starterbot.authentication.AuthenticationHandler;
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

    @Inject Set<AuthenticationHandler> authenticationMethods;

    @Inject
    public AuthenticationListener() {
        super();
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

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        log.info("On guild member join");

        // Getting the user to open a private channel with
        User member = event.getMember().getUser();

        // Creating message string to send user in private channel
        String welcomeMessage =
                String.format(
                        "Welcome to %s! Before you can get access to the server, please verify using one of the following methods:",
                        event.getGuild());

        // Builds the message and adds buttons
        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();

        Set<Button> buttons = new HashSet<>();
        for (AuthenticationHandler auth : authenticationMethods) {
            buttons.add(auth.createButton());
        }
        messageCreateBuilder.addActionRow(buttons).setContent(welcomeMessage);

        // Open private channel with user
        member.openPrivateChannel()
                .flatMap(channel -> channel.sendMessage(messageCreateBuilder.build()))
                .queue();
    }
}
