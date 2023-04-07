package edu.northeastern.cs5500.starterbot.config.command;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/**
 * The slash commands that the program supports.
 */
public interface SlashCommandConfig {
    @Nonnull
    String getName();

    @Nonnull
    CommandData getCommandData();
}
