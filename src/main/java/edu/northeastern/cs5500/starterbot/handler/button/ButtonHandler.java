package edu.northeastern.cs5500.starterbot.handler.button;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/** Interface class for a ButtonHandler. */
public interface ButtonHandler {

    /**
     * Gets the name for the handler.
     *
     * @return the name of the handler.
     */
    @Nonnull
    String getName();

    /**
     * Check if this handler is for a given button.
     *
     * @return True if the handler is expected to handle the button, false otherwise.
     */
    boolean isHandlerFor(String buttonId);

    /**
     * Runs after assigned button is clicked.
     *
     * @param event - Button click.
     */
    void handleButtonInteraction(ButtonInteractionEvent event);
}
