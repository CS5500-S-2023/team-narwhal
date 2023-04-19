package edu.northeastern.cs5500.starterbot.listener;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import javax.inject.Singleton;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Module
public class ListenerModule {
    // https://dagger.dev/dev-guide/multibindings.html
    @Singleton
    @Provides
    @IntoSet
    public ListenerAdapter provideButtonListener(ButtonListener buttonListener) {
        return buttonListener;
    }

    @Singleton
    @Provides
    @IntoSet
    public ListenerAdapter provideGuildMemberJoinListener(
            GuildMemberJoinListener guildMemberJoinListener) {
        return guildMemberJoinListener;
    }

    @Singleton
    @Provides
    @IntoSet
    public ListenerAdapter provideMessageListener(MessageListener messageListener) {
        return messageListener;
    }
}
