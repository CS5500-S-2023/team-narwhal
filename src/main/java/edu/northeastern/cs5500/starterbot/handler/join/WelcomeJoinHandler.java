package edu.northeastern.cs5500.starterbot.handler.join;

import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;
import edu.northeastern.cs5500.starterbot.config.authentication.AuthenticationConfig;
import edu.northeastern.cs5500.starterbot.config.authentication.AuthenticationType;
import edu.northeastern.cs5500.starterbot.controller.MembershipController;
import edu.northeastern.cs5500.starterbot.controller.RoleController;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

/** A class to welcome a member when they join a server. */
@IgnoreInGeneratedReport // Can't test; depends on JDA
public class WelcomeJoinHandler implements JoinHandler {
    @Inject Map<AuthenticationType, AuthenticationConfig> authenticationMethods;
    MembershipController membershipController;
    RoleController roleController;

    @Inject
    public WelcomeJoinHandler(
            MembershipController membershipController, RoleController roleController) {
        this.membershipController = membershipController;
        this.roleController = roleController;
    }

    /** Runs after a member joins a guild. */
    @Override
    @SneakyThrows
    public void handleGuildMemberJoinEvent(GuildMemberJoinEvent event) {
        User user = event.getUser();
        Guild guild = event.getGuild();

        membershipController.addMembership(user, guild);

        String guildName = guild.getName();
        MessageCreateBuilder welcomeMsg = generateWelcomeMsg(guildName);

        // Open a private channel with the user and send the welcome msg
        user.openPrivateChannel().complete().sendMessage(welcomeMsg.build()).queue();
    }

    /**
     * Builds a welcome message with buttons
     *
     * @param guildName the guild name to be used in the welcome message
     * @return a MessageCreateBuilder with a welcome msg and buttons
     */
    public MessageCreateBuilder generateWelcomeMsg(String guildName) {
        // Creating message string to send user in private channel
        String welcomeMessage =
                String.format(
                        "Welcome to \"%s\" server!"
                                + "Before you can get access, "
                                + "please verify using one of the following methods:",
                        guildName);

        // Builds the message and adds buttons
        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();

        List<Button> buttons =
                authenticationMethods.values().stream()
                        .map(AuthenticationConfig::asButton)
                        .sorted(Comparator.comparing(Button::getLabel))
                        .collect(Collectors.toList());

        return messageCreateBuilder
                .addActionRow(Objects.requireNonNull(buttons))
                .setContent(welcomeMessage);
    }
}
