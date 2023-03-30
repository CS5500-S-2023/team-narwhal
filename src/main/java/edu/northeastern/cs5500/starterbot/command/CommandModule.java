package edu.northeastern.cs5500.starterbot.command;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class CommandModule {
    // these commands are what the users can see
    
    @Provides
    @IntoSet
    public SlashCommandHandler provideAuthenticationSettingsCommand(
            AuthenticationSettingsCommand settingsCommand) {
        return settingsCommand;
    }

    @Provides
    @IntoSet
    public ButtonHandler provideAuthenticationSettingsCommandClickHandler(
            AuthenticationSettingsCommand settingsCommand) {
        return settingsCommand;
    }

    @Provides
    @IntoSet
    public SlashCommandHandler provideVerifyCommand(VerifyCommand verifyCommand) {
        return verifyCommand;
    }

    // Demo methods
    @Provides
    @IntoSet
    public SlashCommandHandler provideSayCommand(ButtonCommand sayCommand) {
        return sayCommand;
    }

    @Provides
    @IntoSet
    public SlashCommandHandler providePreferredNameCommand(
            PreferredNameCommand preferredNameCommand) {
        return preferredNameCommand;
    }

    @Provides
    @IntoSet
    public SlashCommandHandler provideButtonCommand(ButtonCommand buttonCommand) {
        return buttonCommand;
    }

    @Provides
    @IntoSet
    public ButtonHandler provideButtonCommandClickHandler(ButtonCommand buttonCommand) {
        return buttonCommand;
    }

    @Provides
    @IntoSet
    public SlashCommandHandler provideDropdownCommand(DropdownCommand dropdownCommand) {
        return dropdownCommand;
    }

    @Provides
    @IntoSet
    public StringSelectHandler provideDropdownCommandMenuHandler(DropdownCommand dropdownCommand) {
        return dropdownCommand;
    }
}
