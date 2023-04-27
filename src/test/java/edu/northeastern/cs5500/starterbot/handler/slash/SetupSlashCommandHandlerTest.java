package edu.northeastern.cs5500.starterbot.handler.slash;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.config.command.SetupSlashCommand;
import edu.northeastern.cs5500.starterbot.controller.GuildController;
import edu.northeastern.cs5500.starterbot.controller.RoleController;
import edu.northeastern.cs5500.starterbot.repository.GuildSetupStateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SetupSlashCommandHandlerTest {

    private SetupSlashCommandHandler testSetUpSlashCommandHandler;

    @BeforeEach
    void setUp() {
        SetupSlashCommand testConfig = new SetupSlashCommand();
        RoleController testRoleController = new RoleController();
        GuildSetupStateRepository testGuildRepo = new GuildSetupStateRepository();
        GuildController testGuildController = new GuildController(testGuildRepo);
        testSetUpSlashCommandHandler =
                new SetupSlashCommandHandler(testConfig, testGuildController, testRoleController);
    }

    @Test
    void getName() {
        assertThat(testSetUpSlashCommandHandler.getName()).isEqualTo("setup");
    }

    @Test
    void handleSlashCommandInteraction() {}

    @Test
    void getPublicRolePermissionsUpdatedMsg() {
        assertThat(testSetUpSlashCommandHandler.getPublicRolePermissionsUpdatedMsg("hello"))
                .isEqualTo("hello\n" + "✅ Server permissions updated.");
    }

    @Test
    void getAllMemberVerifiedMsg() {
        assertThat(testSetUpSlashCommandHandler.getAllMemberVerifiedMsg("yes"))
                .isEqualTo("yes\n" + "✅ \"Verified\" role has been given to all members.");
    }
}
