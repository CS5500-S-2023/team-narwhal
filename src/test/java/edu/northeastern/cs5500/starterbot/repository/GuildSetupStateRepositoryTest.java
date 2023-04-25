package edu.northeastern.cs5500.starterbot.repository;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.GuildState;
import edu.northeastern.cs5500.starterbot.model.SetupState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GuildSetupStateRepositoryTest {
    private GuildSetupStateRepository testGuildRepo;
    private String testGuildId;
    private SetupState testSetupState;
    private GuildState testGuildState;

    @BeforeEach
    void setUp() {
        testGuildRepo = new GuildSetupStateRepository();
        testGuildId = "guild1";
        testSetupState = SetupState.SETUP_COMPLETE;
        testGuildState = GuildState.builder().guildId(testGuildId).state(testSetupState).build();
        testGuildRepo.add(testGuildState);
    }

    @Test
    void get() {
        assertThat(testGuildRepo.get(testGuildId)).isEqualTo(testGuildState);
    }

    @Test
    void delete() {
        testGuildRepo.delete(testGuildId);
        assertThat(testGuildRepo.getAll().size()).isEqualTo(0);
    }
}
