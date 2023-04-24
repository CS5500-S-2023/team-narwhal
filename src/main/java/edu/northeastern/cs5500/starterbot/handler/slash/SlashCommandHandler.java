package edu.northeastern.cs5500.starterbot.handler.slash;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/** Interface class for a SlashCommandHandler */
public interface SlashCommandHandler {

    /**
     * Gets the name of a SlashCommandHandler
     *
     * @return The name of a SlashCommandHandler
     */
    @Nonnull
    String getName();

    /**
     * Runs after a slash command is entered.
     *
     * @param event - A slash command event.
     */
    void handleSlashCommandInteraction(SlashCommandInteractionEvent event);
}
