package edu.northeastern.cs5500.starterbot.authentication;

import edu.northeastern.cs5500.starterbot.command.ButtonHandler;
import javax.annotation.Nonnull;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public interface AuthenticationHandler extends ButtonHandler {
    @Nonnull
    public String getName();

    @Nonnull
    public String getLabel();

    public void authenticate();

    public Button createButton();

    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event);
}
