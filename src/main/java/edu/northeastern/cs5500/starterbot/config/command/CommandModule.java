package edu.northeastern.cs5500.starterbot.config.command;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;

/** Provides all the commands supported by the program, stored in a map. */
@Module
@IgnoreInGeneratedReport
public class CommandModule {
    @Provides
    @IntoMap
    @StringKey(VerifyAllSlashCommand.NAME)
    public SlashCommandConfig provideVerifyAllCommand(VerifyAllSlashCommand verifyAllSlashCommand) {
        return verifyAllSlashCommand;
    }

    @Provides
    @IntoMap
    @StringKey(SetupSlashCommand.NAME)
    public SlashCommandConfig provideSetupCommand(SetupSlashCommand setupSlashCommand) {
        return setupSlashCommand;
    }
}
