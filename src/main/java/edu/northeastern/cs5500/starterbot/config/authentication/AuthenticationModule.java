package edu.northeastern.cs5500.starterbot.config.authentication;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;

/** Provides the authentication types that the program supports, stored in a map. */
@Module
@IgnoreInGeneratedReport
public class AuthenticationModule {
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
