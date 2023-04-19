package edu.northeastern.cs5500.starterbot.handler.slash;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class SlashCommandModule {
    // https://dagger.dev/dev-guide/multibindings.html
    @Provides
    @IntoSet
    public SlashCommandHandler provideVerifySlashCommandHandler(
            VerifySlashCommandHandler verifySlashCommandHandler) {
        return verifySlashCommandHandler;
    }
}
