package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.service.AuthenticationService;
import edu.northeastern.cs5500.starterbot.service.UserPermissionUpdateService;
import edu.northeastern.cs5500.starterbot.view.BotView;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * Listens to various slash command events and delegate to service classes to handle these events,
 * then updates the view.
 */
@Slf4j
public class SlashCommandController {

    private BotView view;
    private AuthenticationService authenticationService;
    private UserPermissionUpdateService userPermissionUpdateService;

    @Inject
    public SlashCommandController(
            BotView view,
            AuthenticationService authenticationService,
            UserPermissionUpdateService userPermissionUpdateService) {
        this.view = view;
        this.authenticationService = authenticationService;
        this.userPermissionUpdateService = userPermissionUpdateService;
    }

    /**
     * Handles all types of slash command interaction events supported by the program.
     *
     * @param event a SlashCommandInteractionEvent object
     */
    public void handleSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("onSlashCommandInteraction: {}", event.getName());
        try {
            String slashCommandType = event.getName();
            switch (slashCommandType) {
                case "verify":
                    boolean isNull = (event.getMember() == null);
                    log.info("The member is null: " + isNull);
                    handleVerifySlashCommand(event);
                    // TODO: once user is verified, assign verified role? delete dm?
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

    /**
     * Helper to handle the /verify command interaction event.
     *
     * @param event a SlashCommandInteractionEvent object
     */
    private void handleVerifySlashCommand(@Nonnull SlashCommandInteractionEvent event) {
        User user = event.getUser();
        String userEventId = user.getId();

        // 1. verify user answer
        // get the user input from the event
        String userInput = Objects.requireNonNull(event.getOption("answer")).getAsString();
        // verify user and return a message that indicates their verified status
        Boolean verifiedStatus = authenticationService.authenticateUser(userEventId, userInput);

        if (verifiedStatus) {
            // 2. update user permission
            // get the guildId using the event userId
            String guildId =
                    userPermissionUpdateService.getGuildIdForEventUser(event.getUser().getId());
            Guild guild = event.getJDA().getGuildById(guildId);

            if (guild != null) {
                userPermissionUpdateService.removeUnverifiedRoleFromUser(guild, user);
                event.reply("You are verified!").queue();
            } else {
                throw new RuntimeException("Can't find guild.");
            }
        } else {
            event.reply("You've entered the wrong answer, please try again.").queue();
        }
    }
}
