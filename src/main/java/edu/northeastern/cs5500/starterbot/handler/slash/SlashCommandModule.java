package edu.northeastern.cs5500.starterbot.handler.slash;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;
import edu.northeastern.cs5500.starterbot.config.command.SetupSlashCommand;
import edu.northeastern.cs5500.starterbot.config.command.VerifyAllSlashCommand;

/** Provides the slash command handlers that the program supports, stored in a map. */
@IgnoreInGeneratedReport
@Module
public class SlashCommandModule {
    @Provides
    @IntoMap
    @StringKey(VerifyAllSlashCommand.NAME)
    public SlashCommandHandler provideVerifyAllSlashCommandHandler(
            VerifyAllSlashCommandHandler verifyAllSlashCommandHandler) {
        return verifyAllSlashCommandHandler;
    }

    @Provides
    @IntoMap
    @StringKey(SetupSlashCommand.NAME)
    public SlashCommandHandler provideSetupSlashCommandHandler(
            SetupSlashCommandHandler setupSlashCommandHandler) {
        return setupSlashCommandHandler;
    }
}
