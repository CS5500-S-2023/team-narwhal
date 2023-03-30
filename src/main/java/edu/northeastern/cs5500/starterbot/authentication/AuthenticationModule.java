package edu.northeastern.cs5500.starterbot.authentication;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class AuthenticationModule {
    @Provides
    @IntoSet
    public CaptchaAuthentication provideCaptchaAuthentication(
            CaptchaAuthentication captchaAuthentication) {
        return captchaAuthentication;
    }

    @Provides
    @IntoSet
    public AuthenticationHandler provideCaptchaAuthenticationHandler(
            CaptchaAuthentication captchaAuthentication) {
        return captchaAuthentication;
    }

    @Provides
    @IntoSet
    public EmailAuthentication provideEmailAuthentication(EmailAuthentication emailAuthentication) {
        return emailAuthentication;
    }

    @Provides
    @IntoSet
    public AuthenticationHandler provideEmailAuthenticationInterHandler(
            EmailAuthentication emailAuthentication) {
        return emailAuthentication;
    }

    @Provides
    @IntoSet
    public TextAuthentication provideTextAuthentication(TextAuthentication textAuthentication) {
        return textAuthentication;
    }

    @Provides
    @IntoSet
    public AuthenticationHandler provideTextAuthenticationHandler(
            TextAuthentication textAuthentication) {
        return textAuthentication;
    }
}
