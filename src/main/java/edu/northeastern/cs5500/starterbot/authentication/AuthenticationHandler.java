package edu.northeastern.cs5500.starterbot.authentication;

import edu.northeastern.cs5500.starterbot.command.ButtonHandler;
import javax.annotation.Nonnull;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public interface AuthenticationHandler extends ButtonHandler {
    // @Nonnull
    // public String getId();

    // @Nonnull
    // public String getLabel();

    // public void authenticate();

    /**
     * If true, this event was handled by a handler that was expecting it.
     * If false, this event was not handled and the next handler should be called.
     * 
     * @param event a private message event from a user
     * @return true if handled; false if not
     */
    public boolean messageReceived(MessageReceivedEvent event);
}
