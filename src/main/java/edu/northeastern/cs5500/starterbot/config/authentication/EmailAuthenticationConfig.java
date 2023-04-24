package edu.northeastern.cs5500.starterbot.config.authentication;

import javax.inject.Inject;
import javax.inject.Singleton;

/** Configuration class for a Email authentication method. */
@Singleton
public class EmailAuthenticationConfig extends AuthenticationConfig {
    @Inject
    public EmailAuthenticationConfig() {
        super("email", "Email");
    }
}
