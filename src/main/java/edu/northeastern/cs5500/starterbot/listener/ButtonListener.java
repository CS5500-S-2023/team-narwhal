package edu.northeastern.cs5500.starterbot.listener;

import edu.northeastern.cs5500.starterbot.authentication.AuthenticationHandler;
import edu.northeastern.cs5500.starterbot.command.ButtonHandler;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Slf4j
public class ButtonListener extends ListenerAdapter {

    @Inject Set<ButtonHandler> buttons;
    @Inject Set<AuthenticationHandler> authenticationMethods;

    @Inject
    public ButtonListener() {
        super();
    }

    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        log.info("onButtonInteraction: {}", event.getButton().getId());
        String id = event.getButton().getId();
        Objects.requireNonNull(id);
        String handlerName = id.split(":", 2)[0];

        for (ButtonHandler buttonHandler : buttons) {
            if (buttonHandler.getName().equals(handlerName)) {
                buttonHandler.onButtonInteraction(event);
                return;
            }
        }

        for (AuthenticationHandler auth : authenticationMethods) {
            if (auth.getName().equals(handlerName)) {
                auth.onButtonInteraction(event);
                return;
            }
        }

        log.error("Unknown button handler: {}", handlerName);
    }
}
