package edu.northeastern.cs5500.starterbot.config.authentication;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TextAuthenticationConfig extends AuthenticationConfig {
    @Inject
    public TextAuthenticationConfig() {
        super("text", "SMS/Text");
    }
}
