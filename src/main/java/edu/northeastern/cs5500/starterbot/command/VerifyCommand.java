package edu.northeastern.cs5500.starterbot.command;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

@Singleton
@Slf4j
public class VerifyCommand implements SlashCommandHandler {

    @Inject
    public VerifyCommand() {
        // Defined public and empty for Dagger injection
    }

    @Override
    @Nonnull
    public String getName() {
        return "verify";
    }

    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Ask the bot to reply with the provided text")
                .addOption(
                        OptionType.STRING,
                        "content",
                        "The bot will reply to your command with the provided text",
                        true);
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /verify");
        TextInput subject =
                TextInput.create("subject", "Subject", TextInputStyle.SHORT)
                        .setPlaceholder("Subject of this ticket")
                        .setMinLength(10)
                        .setMaxLength(100) // or setRequiredRange(10, 100)
                        .build();

        TextInput body =
                TextInput.create("body", "Body", TextInputStyle.PARAGRAPH)
                        .setPlaceholder("Your concerns go here")
                        .setMinLength(30)
                        .setMaxLength(1000)
                        .build();

        Modal modal =
                Modal.create("modmail", "Modmail")
                        .addActionRows(ActionRow.of(subject), ActionRow.of(body))
                        .build();

        event.replyModal(modal).queue();
    }

    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        log.info("event: member join");
    }
}
