package edu.northeastern.cs5500.starterbot.config.authentication;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EmailAuthenticationConfig extends AuthenticationConfig {
    @Inject
    public EmailAuthenticationConfig() {
        super("email", "Email");
    }

}
