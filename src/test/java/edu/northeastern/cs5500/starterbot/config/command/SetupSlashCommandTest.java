package edu.northeastern.cs5500.starterbot.config.command;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SetupSlashCommandTest {

    private SetupSlashCommand testSetUpaSlashCommand;

    @BeforeEach
    void setUp() {
        testSetUpaSlashCommand = new SetupSlashCommand();
    }

    @Test
    void getName() {
        assertThat(testSetUpaSlashCommand.getName()).isEqualTo("setup");
    }

    @Test
    void getOptionName() {
        assertThat(testSetUpaSlashCommand.getOptionName()).isEqualTo("verify-all");
    }

    @Test
    void getCommandData() {
        String testDescription =
                "Setup server: update @everyone permissions; optionally auto-verify all current members.";
        assertThat(testSetUpaSlashCommand.getCommandData().toData().get("name")).isEqualTo("setup");
        assertThat(testSetUpaSlashCommand.getCommandData().toData().get("description"))
                .isEqualTo(testDescription);
    }
}
