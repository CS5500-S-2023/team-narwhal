package edu.northeastern.cs5500.starterbot.handler.button;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class ButtonModule {
    // https://dagger.dev/dev-guide/multibindings.html
    @Provides
    @IntoSet
    public ButtonHandler provideCaptchaButtonHandler(CaptchaButtonHandler captchaButtonHandler) {
        return captchaButtonHandler;
    }
}
