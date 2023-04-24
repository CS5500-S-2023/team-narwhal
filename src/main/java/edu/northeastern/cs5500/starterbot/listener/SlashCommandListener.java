package edu.northeastern.cs5500.starterbot.listener;

import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;
import edu.northeastern.cs5500.starterbot.handler.slash.SlashCommandHandler;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/** A generic SlashCommandListener that detects any slash command interaction. */
@IgnoreInGeneratedReport
@Slf4j
@Singleton
public class SlashCommandListener extends ListenerAdapter {
    @Inject Map<String, Provider<SlashCommandHandler>> slashCommandHandlers;

    @Inject
    public SlashCommandListener() {
        super();
    }

    /** When a user enters a slash command. */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        var name = event.getName();
        for (Entry<String, Provider<SlashCommandHandler>> entry : slashCommandHandlers.entrySet()) {
            if (entry.getKey().equals(name)) {
                entry.getValue().get().handleSlashCommandInteraction(event);
                return;
            }
        }
        log.error("Unknown slash command: {}", event.getName());
        event.reply("Sorry, I don't know how to handle that command.").queue();
    }
}
