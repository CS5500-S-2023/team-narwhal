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
// part of the service, controlled by controller
public class VerifyCommand implements SlashCommandHandler {
    
    @Inject GenericRepository<AuthenticationChallenge> challengeRepository;

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
        String userInput = Objects.requireNonNull(event.getOption("content")).getAsString();

        String discordUserId = event.getUser().getId();

        String captchaAnswer = userPreferenceController.getPreferredNameForUser(discordUserId);

        userPreferenceController.setPreferredNameForUser(discordUserId, preferredName);

        if (oldPreferredName == null) {
            event.reply("Your preferred name has been set to " + preferredName).queue();
        } else {
            event.reply(
                            "Your preferred name has been changed from "
                                    + oldPreferredName
                                    + " to "
                                    + preferredName)
                    .queue();
        }

    }

}
