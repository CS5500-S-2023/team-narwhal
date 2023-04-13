package edu.northeastern.cs5500.starterbot.config.authentication;

import javax.annotation.Nonnull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public abstract class AuthenticationConfig {

    @Nonnull public String id;
    @Nonnull public String name = "authenticate"; // button type
    @Nonnull public String label;

    protected AuthenticationConfig(@Nonnull String id, @Nonnull String label) {
        this.id = id;
        this.label = label;
    }
}
