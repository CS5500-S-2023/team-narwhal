package edu.northeastern.cs5500.starterbot.handler.slash;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface SlashCommandHandler {
    @Nonnull
    String getName();

    @Nonnull
    CommandData getCommandData();

    void handleSlashCommandInteraction(SlashCommandInteractionEvent event);
}
