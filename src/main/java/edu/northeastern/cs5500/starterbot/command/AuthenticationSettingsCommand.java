package edu.northeastern.cs5500.starterbot.command;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

@Singleton
@Slf4j
public class AuthenticationSettingsCommand implements SlashCommandHandler, ButtonHandler {

    @Inject
    public AuthenticationSettingsCommand() {}

    @Override
    @Nonnull
    public String getName() {
        return "settings";
    }

    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Set authentication and profile settings");
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /settings");

        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();
        messageCreateBuilder =
                messageCreateBuilder.addActionRow(
                        Button.primary(this.getName() + ":captcha", "Captcha"),
                        Button.primary(this.getName() + ":twoFactor", "2-factor auth"),
                        Button.primary(this.getName() + ":email", "Email"));
        messageCreateBuilder =
                messageCreateBuilder.setContent(
                        "Which authentication method do you want to set-up?");
        event.reply(messageCreateBuilder.build()).queue();
    }

    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        event.reply("You chose " + event.getButton().getLabel()).queue();
    }
}
