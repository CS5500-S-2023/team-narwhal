package edu.northeastern.cs5500.starterbot.config.authentication;

import javax.inject.Inject;
import javax.inject.Singleton;

/** Configuration class for a captcha authentication method. */
@Singleton
public class CaptchaAuthenticationConfig extends AuthenticationConfig {

    @Inject
    public CaptchaAuthenticationConfig() {
        super("captcha", "CAPTCHA");
    }
}
