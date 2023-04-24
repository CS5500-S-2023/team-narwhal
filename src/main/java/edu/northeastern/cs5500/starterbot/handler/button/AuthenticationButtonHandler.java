package edu.northeastern.cs5500.starterbot.handler.button;

import edu.northeastern.cs5500.starterbot.controller.AuthenticationController;
import javax.annotation.Nonnull;
import javax.inject.Inject;

/** Abstract class for all authentication method buttons. */
public abstract class AuthenticationButtonHandler implements ButtonHandler {
    @Inject AuthenticationController authenticationController;

    private static final String NAME = "authenticate";
    @Nonnull private String id;

    protected AuthenticationButtonHandler(@Nonnull String id) {
        this.id = id;
    }

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
     * Gets the id for the handler.
     *
     * @return the id
     */
    @Nonnull
    public String getId() {
        return id;
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
