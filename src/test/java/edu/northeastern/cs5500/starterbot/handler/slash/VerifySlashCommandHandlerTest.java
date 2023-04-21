package edu.northeastern.cs5500.starterbot.handler.slash;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VerifySlashCommandHandlerTest {

    private VerifySlashCommandHandler verifySlashCommandHandler;

    @BeforeEach
    void setUp() {
        verifySlashCommandHandler = new VerifySlashCommandHandler();
    }

    @Test
    void getName() {
        assertThat(verifySlashCommandHandler.getName()).matches("verify");
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
