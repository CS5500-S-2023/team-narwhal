package edu.northeastern.cs5500.starterbot.listener;

import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;
import edu.northeastern.cs5500.starterbot.handler.message.MessageHandler;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@IgnoreInGeneratedReport
@Singleton
public class MessageListener extends ListenerAdapter {
    @Inject Set<MessageHandler> messageHandlers;

    @Inject
    public MessageListener() {
        super();
    }

    /**
     * Listen to onMessageReceived events. Catching exceptions since onMessageReceived abstract
     * method does not have any checked exceptions
     */
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.getChannelType() != ChannelType.PRIVATE || event.getAuthor().isBot()) {
            // We only listen to direct/private messages
            return;
        }

        for (MessageHandler messageHandler : messageHandlers) {
            messageHandler.handleMessage(event);
        }
    }
}
