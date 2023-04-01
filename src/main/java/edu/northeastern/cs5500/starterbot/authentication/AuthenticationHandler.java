package edu.northeastern.cs5500.starterbot.authentication;

import edu.northeastern.cs5500.starterbot.command.ButtonHandler;

import java.beans.EventHandler;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public interface AuthenticationHandler extends ButtonHandler {
    @Nonnull
    public String getId();

    @Nonnull
    public String getLabel();

    public void authenticate();

    public Button createButton();

    public String getAnswer();

    
}
