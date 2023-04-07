package edu.northeastern.cs5500.starterbot.config.authentication;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

/**
 * Provides the authentication types that the program supports, stored in a map.
 */
@Module
public class AuthenticationModule {
    // https://dagger.dev/dev-guide/multibindings.html
    @Provides
    @IntoMap
    @AuthenticationTypeMapKey(AuthenticationType.CAPTCHA)
    public AuthenticationConfig provideCaptchaAuthenticationConfig(
            CaptchaAuthenticationConfig captchaAuthenticationConfig) {
        return captchaAuthenticationConfig;
    }

    @Provides
    @IntoMap
    @AuthenticationTypeMapKey(AuthenticationType.EMAIL)
    public AuthenticationConfig provideEmailAuthenticationConfig(
            EmailAuthenticationConfig emailAuthenticationConfig) {
        return emailAuthenticationConfig;
    }

    @Provides
    @IntoMap
    @AuthenticationTypeMapKey(AuthenticationType.TEXT)
    public AuthenticationConfig provideTextAuthenticationConfig(
            TextAuthenticationConfig textAuthenticationConfig) {
        return textAuthenticationConfig;
    }
}
