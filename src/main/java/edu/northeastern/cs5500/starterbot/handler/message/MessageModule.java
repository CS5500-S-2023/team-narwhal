package edu.northeastern.cs5500.starterbot.handler.message;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;

/** Provides the message handlers that the program supports, stored in a set. */
@IgnoreInGeneratedReport
@Module
public class MessageModule {
    @Provides
    @IntoSet
    public MessageHandler provideAuthenticateMessageHandler(
            AuthenticateMessageHandler authenticateMessageHandler) {
        return authenticateMessageHandler;
    }
}
