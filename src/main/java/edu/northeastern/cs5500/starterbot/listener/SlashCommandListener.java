package edu.northeastern.cs5500.starterbot.listener;

import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;
import edu.northeastern.cs5500.starterbot.handler.slash.SlashCommandHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/** A generic SlashCommandListener that detects any slash command interaction. */
@IgnoreInGeneratedReport
@Slf4j
@Singleton
public class SlashCommandListener extends ListenerAdapter {
    @Inject Set<SlashCommandHandler> slashCommandHandlers;

    @Inject
    public SlashCommandListener() {
        super();
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        for (SlashCommandHandler slashCommand : slashCommandHandlers) {
            if (slashCommand.getName().equals(event.getName())) {
                slashCommand.handleSlashCommandInteraction(event);
                return;
            }
        }
        log.error("Unknown slash command: {}", event.getName());
        event.reply("Sorry, I don't know how to handle that command.").queue();
    }

    public @Nonnull Collection<CommandData> allCommandData() {
        Collection<CommandData> commandData =
                slashCommandHandlers.stream()
                        .map(SlashCommandHandler::getCommandData)
                        .collect(Collectors.toList());
        if (commandData == null) {
            return new ArrayList<>();
        }
        return commandData;
    }
}
