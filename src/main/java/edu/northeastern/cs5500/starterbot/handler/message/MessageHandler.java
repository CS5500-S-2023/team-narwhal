package edu.northeastern.cs5500.starterbot.handler.message;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface MessageHandler {
    void handleMessage(@Nonnull MessageReceivedEvent event);
}
