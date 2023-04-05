package edu.northeastern.cs5500.starterbot.config.authentication;

import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AuthenticationConfig {
    @Nonnull public String id;
    @Nonnull public String name = "authenticate"; // handlerName
    @Nonnull public String label;

    protected AuthenticationConfig(@Nonnull String id, @Nonnull String label) {
        this.id = id;
        this.label = label;
    }

    @Nonnull
    public String getId() {
        return id;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public String getLabel() {
        return label;
    }
}
