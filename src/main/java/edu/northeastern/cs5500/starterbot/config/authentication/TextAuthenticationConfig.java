package edu.northeastern.cs5500.starterbot.config.authentication;

import javax.inject.Inject;
import javax.inject.Singleton;

/** Configuration class for a Text authentication method. */
@Singleton
public class TextAuthenticationConfig extends AuthenticationConfig {
    @Inject
    public TextAuthenticationConfig() {
        super("text", "SMS/Text");
    }
}
