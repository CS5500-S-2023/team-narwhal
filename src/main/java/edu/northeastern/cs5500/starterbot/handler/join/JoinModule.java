package edu.northeastern.cs5500.starterbot.handler.join;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

/** Provides the join handlers that the program supports, stored in a set. */
@Module
public class JoinModule {
    @Provides
    @IntoSet
    public JoinHandler provideWelcomeJoinHandler(WelcomeJoinHandler welcomeJoinHandler) {
        return welcomeJoinHandler;
    }
}
