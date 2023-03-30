package edu.northeastern.cs5500.starterbot.authentication;

import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

@Slf4j
public class Authentication implements AuthenticationHandler {
    @Nonnull public String name;
    @Nonnull public String label;
    ;

    public Authentication(String name, String label) {
        this.name = name;
        this.label = label;
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

    public Button createButton() {
        return Button.primary("authenticate:" + name, label);
    }

    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        //
    }
}
