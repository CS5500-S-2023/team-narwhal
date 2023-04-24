package edu.northeastern.cs5500.starterbot.handler.button;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

/** Provides the button handlers that the program supports, stored in a set. */
@Module
public class ButtonModule {
    @Provides
    @IntoSet
    public ButtonHandler provideCaptchaButtonHandler(CaptchaButtonHandler captchaButtonHandler) {
        return captchaButtonHandler;
    }
}
