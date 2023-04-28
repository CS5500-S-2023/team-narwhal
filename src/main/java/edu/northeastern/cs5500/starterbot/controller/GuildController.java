package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;
import edu.northeastern.cs5500.starterbot.model.GuildState;
import edu.northeastern.cs5500.starterbot.model.SetupState;
import edu.northeastern.cs5500.starterbot.repository.GuildSetupStateRepository;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Handles challenges and their business logic: 1. Create a challenge 2. Update a challenge 3.
 * Delete a challenge 4. Update attempts
 */
@Singleton
@IgnoreInGeneratedReport // Can't test; depends on JDA Guild
public class GuildController {
    GuildSetupStateRepository guildSetupStateRepository;

    @Inject
    public GuildController(GuildSetupStateRepository guildSetupStateRepository) {
        this.guildSetupStateRepository = guildSetupStateRepository;
    }

    /**
     * Starts the guild setup.
     *
     * @param guildId - The ID for the guild to set up.
     */
    public void startGuildSetup(String guildId) {
        GuildState guildState =
                GuildState.builder().guildId(guildId).state(SetupState.SETUP_IN_PROGRESS).build();
        guildSetupStateRepository.add(guildState);
    }

    /**
     * Updates the guild state in the guildSetupStateRepository after the setup is complete.
     *
     * @param guildId - The ID for the guild the setup is complete for.
     */
    public void onGuildSetupComplete(String guildId) {
        GuildState guildState =
                GuildState.builder().guildId(guildId).state(SetupState.SETUP_COMPLETE).build();
        guildSetupStateRepository.update(guildState);
    }

    /**
     * Updates the guild state in the guildSetupStateRepository after the setup fails.
     *
     * @param guildId - The ID for the guild whose setup failed.
     */
    public void onGuildSetupFail(String guildId) {
        GuildState guildState =
                GuildState.builder().guildId(guildId).state(SetupState.SETUP_FAILED).build();
        guildSetupStateRepository.update(guildState);
    }
}
