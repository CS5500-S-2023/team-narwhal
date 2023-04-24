package edu.northeastern.cs5500.starterbot.listener;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import javax.inject.Singleton;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/** Provides the listeners that the program supports, stored in a set. */
@Module
public class ListenerModule {
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
