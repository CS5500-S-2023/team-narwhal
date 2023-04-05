package edu.northeastern.cs5500.starterbot.config.command;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class CommandModule {
    @Provides
    @IntoSet
    public SlashCommandHandler provideVerifyCommand(VerifySlashCommand verifySlashCommand) {
        return verifySlashCommand;
    }

    // feedback slash command
}
