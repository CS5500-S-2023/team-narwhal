package edu.northeastern.cs5500.starterbot.model;

/** The states of a {@link GuildState}. */
public enum SetupState {
    // UNKNOWN in case we get a state from the DB that doesn't make sense
    UNKNOWN,
    // Guild has not been set-up yet
    SETUP_NOT_STARTED,
    // Guild set-up is in progress
    SETUP_IN_PROGRESS,
    // Guild set-up complete
    SETUP_COMPLETE,
    // Set-up ran into an issue; Guild could not be set-up
    SETUP_FAILED;
}
