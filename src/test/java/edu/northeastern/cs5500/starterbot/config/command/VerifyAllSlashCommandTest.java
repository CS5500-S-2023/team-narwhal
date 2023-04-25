package edu.northeastern.cs5500.starterbot.config.command;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VerifyAllSlashCommandTest {
    private VerifyAllSlashCommand testVerifyAllSlashCommand;

    @BeforeEach
    void setUp() {
        testVerifyAllSlashCommand = new VerifyAllSlashCommand();
    }

    @Test
    void getName() {
        assertThat(testVerifyAllSlashCommand.getName()).isEqualTo("verify-all");
    }

    @Test
    void getCommandData() {
        assertThat(testVerifyAllSlashCommand.getCommandData().toData().get("name"))
                .isEqualTo("verify-all");
        assertThat(testVerifyAllSlashCommand.getCommandData().toData().get("description"))
                .isEqualTo("Add verified role to all current members of servers.");
    }
}
