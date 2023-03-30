package edu.northeastern.cs5500.starterbot.authentication;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

@Singleton
@Slf4j
public class TextAuthentication extends Authentication {
    @Inject
    public TextAuthentication() {
        super("text", "SMS / Text");
    }

    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        event.reply(event.getButton().getLabel()).queue();
    }
}
