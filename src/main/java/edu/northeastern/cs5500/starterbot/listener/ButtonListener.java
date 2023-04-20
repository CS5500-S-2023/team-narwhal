package edu.northeastern.cs5500.starterbot.listener;

import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;
import edu.northeastern.cs5500.starterbot.handler.button.ButtonHandler;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/** A generic ButtonListener that detects any button interaction. */
@IgnoreInGeneratedReport
@Slf4j
@Singleton
public class ButtonListener extends ListenerAdapter {
    @Inject Set<ButtonHandler> buttonHandlers;

    @Inject
    public ButtonListener() {
        super();
    }

    /** When a user clicks a button */
    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        String buttonId = Objects.requireNonNull(event.getButton().getId());
        for (ButtonHandler buttonHandler : buttonHandlers) {
            if (buttonHandler.isHandlerFor(buttonId)) {
                buttonHandler.handleButtonInteraction(event);
                return;
            }
        }

        log.error("Unknown button press: {}", buttonId);
        event.reply("Sorry, I don't know how to handle that button.").queue();
    }
}
