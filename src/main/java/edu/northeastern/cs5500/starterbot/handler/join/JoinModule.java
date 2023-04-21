package edu.northeastern.cs5500.starterbot.handler.join;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class JoinModule {
    // https://dagger.dev/dev-guide/multibindings.html
    @Provides
    @IntoSet
    public JoinHandler provideWelcomeJoinHandler(WelcomeJoinHandler welcomeJoinHandler) {
        return welcomeJoinHandler;
    }
}
