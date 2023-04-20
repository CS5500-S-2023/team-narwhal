package edu.northeastern.cs5500.starterbot.handler.button;

import edu.northeastern.cs5500.starterbot.controller.AuthenticationController;
import javax.annotation.Nonnull;
import javax.inject.Inject;

public abstract class AuthenticationButtonHandler implements ButtonHandler {
    @Inject AuthenticationController authenticationController;

    private static final String NAME = "authenticate";
    @Nonnull private String id;

    protected AuthenticationButtonHandler(@Nonnull String id) {
        this.id = id;
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

    @Nonnull
    public String getId() {
        return id;
    }

    public boolean isHandlerFor(String buttonId) {
        return buttonId.equals(String.format("%s:%s", getName(), getId()));
    }
}
