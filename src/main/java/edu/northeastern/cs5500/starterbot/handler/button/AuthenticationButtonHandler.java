package edu.northeastern.cs5500.starterbot.handler.button;

import edu.northeastern.cs5500.starterbot.controller.AuthenticationController;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.Data;

/** Abstract class for all authentication method buttons. */
@Getter
@Setter
public abstract class AuthenticationButtonHandler implements ButtonHandler {
    @Inject AuthenticationController authenticationController;

    private static final String NAME = "authenticate";
    @Nonnull private String id;

    /**
     * Gets the name for the handler.
     *
     * @return "authenticate"
     */
    @Nonnull
    public String getName() {
        return NAME;
    }

    /**
     * Check if this handler is for a given button.
     *
     * @return True if the handler is expected to handle the button, false otherwise.
     */
    public boolean isHandlerFor(String buttonId) {
        return buttonId.equals(String.format("%s:%s", getName(), getId()));
    }
}
