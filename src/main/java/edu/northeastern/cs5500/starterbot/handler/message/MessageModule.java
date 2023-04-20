package edu.northeastern.cs5500.starterbot.handler.message;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class MessageModule {
    // https://dagger.dev/dev-guide/multibindings.html
    @Provides
    @IntoSet
    public MessageHandler provideAuthenticateMessageHandler(
            AuthenticateMessageHandler authenticateMessageHandler) {
        return authenticateMessageHandler;
    }
}
