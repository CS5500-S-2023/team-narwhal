package edu.northeastern.cs5500.starterbot.model;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SetupStateTest {
    private SetupState unknown;
    private SetupState notStarted;
    private SetupState inProgress;
    private SetupState complete;
    private SetupState failed;

    @BeforeEach
    void setUp() {
        unknown = SetupState.UNKNOWN;
        notStarted = SetupState.SETUP_NOT_STARTED;
        inProgress = SetupState.SETUP_IN_PROGRESS;
        complete = SetupState.SETUP_COMPLETE;
        failed = SetupState.SETUP_FAILED;
    }

    @Test
    void values() {
        assertThat(SetupState.values())
                .asList()
                .containsExactly(unknown, notStarted, inProgress, complete, failed);
    }

    @Test
    void valueOf() {
        assertThat(SetupState.valueOf("UNKNOWN")).isEqualTo(unknown);
        assertThat(SetupState.valueOf("SETUP_NOT_STARTED")).isEqualTo(notStarted);
        assertThat(SetupState.valueOf("SETUP_IN_PROGRESS")).isEqualTo(inProgress);
        assertThat(SetupState.valueOf("SETUP_COMPLETE")).isEqualTo(complete);
        assertThat(SetupState.valueOf("SETUP_FAILED")).isEqualTo(failed);
    }
}
