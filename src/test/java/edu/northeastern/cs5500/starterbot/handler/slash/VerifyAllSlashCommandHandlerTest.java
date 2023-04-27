package edu.northeastern.cs5500.starterbot.handler.slash;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.config.command.VerifyAllSlashCommand;
import edu.northeastern.cs5500.starterbot.controller.GuildController;
import edu.northeastern.cs5500.starterbot.controller.RoleController;
import edu.northeastern.cs5500.starterbot.repository.GuildSetupStateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VerifyAllSlashCommandHandlerTest {
    private VerifyAllSlashCommandHandler verifyAllSlashCommandHandler;

    @BeforeEach
    void setUp() {
        VerifyAllSlashCommand config = new VerifyAllSlashCommand();
        GuildSetupStateRepository guildSetupStateRepository = new GuildSetupStateRepository();
        GuildController guildController = new GuildController(guildSetupStateRepository);
        RoleController roleController = new RoleController();
        verifyAllSlashCommandHandler =
                new VerifyAllSlashCommandHandler(config, guildController, roleController);
    }

    @Test
    void getName() {
        assertThat(verifyAllSlashCommandHandler.getName()).matches("verify-all");
    }
}
