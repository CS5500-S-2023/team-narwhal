package edu.northeastern.cs5500.starterbot.config.command;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

/** Provides all the commands supported by the program, stored in a set. */
@Module
public class CommandModule {
    @Provides
    @IntoSet
    public SlashCommandConfig provideVerifyCommand(VerifySlashCommand verifySlashCommand) {
        return verifySlashCommand;
    }

    // feedback slash command
}
