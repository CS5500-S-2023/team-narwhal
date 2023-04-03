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
        return Commands.slash(getName(), "xxx")
                .addOption(
                        OptionType.STRING,
                        "content",
                        "xxx",
                        true);
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /verify");
        String userInput = Objects.requireNonNull(event.getOption("content")).getAsString();
        String discordUserId = event.getUser().getId();
        String captchaAnswer = getAuthencationAnswerForMemberId(discordUserId);
        Boolean correct = userInput.equals(captchaAnswer);
        if (correct){
            
        }
    }

    @Nonnull
    protected String getAuthencationAnswerForMemberId(String discordMemberId) {
        for (AuthenticationChallenge currentUserAuth : challengeRepository.getAll()) {
            if (currentUserAuth.getDiscordUserId().equals(discordMemberId)) {
                return currentUserAuth.getAnswer();
            }
        }
        // throw custom user not found exceptions
        throw new RuntimeException("User not found!");
    }
}
