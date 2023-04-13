package edu.northeastern.cs5500.starterbot.handler.join;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import edu.northeastern.cs5500.starterbot.config.authentication.AuthenticationConfig;
import edu.northeastern.cs5500.starterbot.config.authentication.AuthenticationType;
import edu.northeastern.cs5500.starterbot.service.UserEnterService;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

public class WelcomeJoinHandler implements JoinHandler {
    @Inject Map<AuthenticationType, AuthenticationConfig> authenticationMethods;
    @Inject UserEnterService userEnterService;

    @Inject
    public WelcomeJoinHandler() {
        // Left blank for dagger injection
    }

    @Override
    public void handleGuildMemberJoinEvent(GuildMemberJoinEvent event) {
        User user = event.getUser();

        // add the event user id and the guild id to the repo for later use
        String userId = user.getId();
        String guildId = event.getGuild().getId();
        userEnterService.mapEventUserGuildId(userId, guildId);

        // use the view to  build the welcome msg
        String guildName = event.getGuild().getName();
        MessageCreateBuilder welcomeMsg = generateWelcomeMsg(guildName);

        // Open a private channel with the user and send the welcome msg
        // TODO: add try catch
        PrivateChannel channel = user.openPrivateChannel().complete();
        channel.sendMessage(welcomeMsg.build()).queue();
    }

    
    /**
     * Builds a welcome message with buttons, used by UserEnterController.
     *
     * @param guildName the guild name to be used in the welcome message
     * @return an MessageCreateBuilder with a welcome msg and buttons
     */
    public MessageCreateBuilder generateWelcomeMsg(String guildName) {
        // Creating message string to send user in private channel
        String welcomeMessage =
                String.format(
                        "Welcome to %s! Before you can get access to the server, please verify using one of "
                                + "the following methods:",
                        guildName);

        // Builds the message and adds buttons
        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();

        // TODO: Does the order of buttons matter?
        Set<Button> buttons = new HashSet<>();
        for (AuthenticationConfig auth : authenticationMethods.values()) {
            buttons.add(auth.asButton());
        }
        List<Button> sortedButtons = buttons.stream().sorted(Comparator.comparing(Button::getLabel)).collect(Collectors.toList());
        return messageCreateBuilder.addActionRow(sortedButtons).setContent(welcomeMessage);
    }
    
}
