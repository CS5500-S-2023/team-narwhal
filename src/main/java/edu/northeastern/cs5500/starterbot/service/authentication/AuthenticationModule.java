package edu.northeastern.cs5500.starterbot.authentication;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class AuthenticationModule {
    @Binds
    @IntoMap
    @ViewHolderKey(AuthenticationType.CAPTCHA)
    public AuthenticationHandler provideCaptchaAuthenticationHandler(
            CaptchaAuthenticationHandler captchaAuthenticationHandler) {
        return captchaAuthenticationHandler;
    }


    @Binds
    @IntoMap
    @ViewHolderKey(AuthenticationType.EMAIL)
    public AuthenticationHandler provideEmailAuthenticationHandler(
            EmailAuthenticationHandler emailAuthenticationHandler) {
        return emailAuthenticationHandler;
    }


    @Binds
    @IntoMap
    @ViewHolderKey(AuthenticationType.TEXT)
    public AuthenticationHandler provideTextAuthenticationHandler(
            TextAuthenticationHandler textAuthenticationHandler) {
        return textAuthenticationHandler;
    }
}
