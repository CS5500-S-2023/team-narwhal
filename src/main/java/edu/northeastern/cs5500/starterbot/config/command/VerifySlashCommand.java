package edu.northeastern.cs5500.starterbot.config.command;

import edu.northeastern.cs5500.starterbot.model.AuthenticationChallenge;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

@Singleton
@Slf4j
public class VerifySlashCommand implements SlashCommandConfig {

  @Inject
  GenericRepository<AuthenticationChallenge> challengeRepository;

  @Inject
  public VerifySlashCommand() {
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
    return Commands.slash(getName(), "Tell the bot your answer to this captcha")
        .addOption(OptionType.STRING, "answer",
            "The bot will use this input to authenticate", true);
  }
}
