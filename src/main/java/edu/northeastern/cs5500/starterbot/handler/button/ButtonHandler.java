package edu.northeastern.cs5500.starterbot.handler.button;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public interface ButtonHandler {
    @Nonnull
    String getName();

    boolean isHandlerFor(String buttonId);

    void handleButtonInteraction(ButtonInteractionEvent event);
}
