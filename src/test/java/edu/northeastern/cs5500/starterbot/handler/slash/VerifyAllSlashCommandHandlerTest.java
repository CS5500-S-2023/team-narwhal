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
        GuildSetupStateRepository guildSetupStateRepository = new GuildSetupStateRepository();
        RoleController roleController = new RoleController();
        VerifyAllSlashCommand config = new VerifyAllSlashCommand();
        GuildController guildController = new GuildController(guildSetupStateRepository);
        verifyAllSlashCommandHandler =
                new VerifyAllSlashCommandHandler(config, guildController, roleController);
    }

    @Test
    void getName() {
        assertThat(verifyAllSlashCommandHandler.getName()).matches("verify-all");
    }

    @Test
    void getCommandData() {
        /*
        CommandData commandData =  Commands.slash("verify", "Tell the bot what name to address you with")
            .addOptions(
                new OptionData(
                    OptionType.STRING,
                    "name",
                    "The bot will use this name to talk to you going forward")
                    .setRequired(true));
        assertThat(verifySlashCommandHandler.getCommandData())
         */
    }

    @Test
    void handleSlashCommandInteraction() {}
}
