package edu.northeastern.cs5500.starterbot.config.command;

import edu.northeastern.cs5500.starterbot.repository.ChallengeRepository;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

@Singleton
public class VerifySlashCommand implements SlashCommandConfig {
    ChallengeRepository challengeRepository;

    @Inject
    public VerifySlashCommand(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    @Override
    @Nonnull
    public String getName() {
        return "verify";
    }

    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Tell the bot your answer to this captcha")
                .addOption(
                        OptionType.STRING,
                        "answer",
                        "The bot will use this input to authenticate",
                        true);
    }
}
