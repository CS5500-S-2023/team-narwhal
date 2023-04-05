package edu.northeastern.cs5500.starterbot.config.authentication;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public class AuthenticationModule {
    // https://dagger.dev/dev-guide/multibindings.html
    @Provides
    @IntoMap
    @AuthenticationMapKey(AuthenticationType.CAPTCHA)
    public AuthenticationConfig provideCaptchaAuthenticationConfig(
            CaptchaAuthenticationConfig captchaAuthenticationConfig) {
        return captchaAuthenticationConfig;
    }

    @Provides
    @IntoMap
    @AuthenticationMapKey(AuthenticationType.EMAIL)
    public AuthenticationConfig provideEmailAuthenticationConfig(
            EmailAuthenticationConfig emailAuthenticationConfig) {
        return emailAuthenticationConfig;
    }

    @Provides
    @IntoMap
    @AuthenticationMapKey(AuthenticationType.TEXT)
    public AuthenticationConfig provideTextAuthenticationConfig(
            TextAuthenticationConfig textAuthenticationConfig) {
        return textAuthenticationConfig;
    }
}
