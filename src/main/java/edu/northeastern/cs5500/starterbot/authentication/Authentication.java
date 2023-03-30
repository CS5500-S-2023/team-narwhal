package edu.northeastern.cs5500.starterbot.authentication;

import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

@Slf4j
public class Authentication implements AuthenticationHandler {
    @Nonnull public String id;
    @Nonnull public String name = "authenticate"; // handlerName
    @Nonnull public String label;
    
    // TODO: this authentication probably should also has a status?

    public Authentication(String id, String label) {
        this.id = id;
        this.label = label;
    }

    @Nonnull
    public String getId() {
        return name;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public String getLabel() {
        return label;
    }

    public void authenticate() {
        log.info(String.format("Authenticating using %s", label));
    }
    
    // TODO: change this
    public Button createButton() {
        return Button.primary(name + ":" + id, label);
    }

    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        //
    }
}
