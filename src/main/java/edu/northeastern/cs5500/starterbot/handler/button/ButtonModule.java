package edu.northeastern.cs5500.starterbot.handler.button;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;

/** Provides the button handlers that the program supports, stored in a set. */
@Module
@IgnoreInGeneratedReport
public class ButtonModule {
    @Provides
    @IntoSet
    public ButtonHandler provideCaptchaButtonHandler(CaptchaButtonHandler captchaButtonHandler) {
        return captchaButtonHandler;
    }
}
