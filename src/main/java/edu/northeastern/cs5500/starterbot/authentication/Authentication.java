package edu.northeastern.cs5500.starterbot.authentication;

import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

@Slf4j
public abstract class Authentication implements AuthenticationHandler {
    @Nonnull public String id;
    @Nonnull public String name = "authenticate"; // handlerName
    @Nonnull public String label;
    
    public Authentication(@Nonnull String id, @Nonnull String label) {
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
    
    
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        
        event.reply(event.getButton().getLabel()).queue();
    }

    @Override
    public boolean messageReceived(MessageReceivedEvent event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'messageReceived'");
    }
}
