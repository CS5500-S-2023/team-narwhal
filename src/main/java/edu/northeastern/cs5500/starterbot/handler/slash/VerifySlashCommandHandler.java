package edu.northeastern.cs5500.starterbot.handler.slash;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class VerifySlashCommandHandler implements SlashCommandHandler {
    private static final String name = "verify";

    @Inject
    public VerifySlashCommandHandler() {
        //
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Tell the bot what name to address you with")
                .addOptions(
                        new OptionData(
                                        OptionType.STRING,
                                        "name",
                                        "The bot will use this name to talk to you going forward")
                                .setRequired(true));
    }

    public void handleSlashCommandInteraction(SlashCommandInteractionEvent event) {
        //
    }
}
