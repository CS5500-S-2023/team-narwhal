package edu.northeastern.cs5500.starterbot.handler.button;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public interface ButtonHandler {
    String getName();
    void handleButtonInteraction(ButtonInteractionEvent event);
}
