package edu.northeastern.cs5500.starterbot.handler.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface SlashHandler {
    String getName();
    void handleSlashCommandInteraction(SlashCommandInteractionEvent event);
}
