package edu.northeastern.cs5500.starterbot.command;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import nl.captcha.Captcha;

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
        // --- Modal code example ---
        // TextInput testString =
        //         TextInput.create("welcome", "Authenticate", TextInputStyle.SHORT)
        //                 .setMinLength(1)
        //                 .build();
        // TextInput message =
        //         TextInput.create("welcome-message", "Message", TextInputStyle.PARAGRAPH)
        //                 .setMinLength(1)
        //                 .setPlaceholder("placeholder")
        //                 .build();

        // Modal modal =
        //         Modal.create("welcome-modal", "Welcome")
        //                 .addActionRows(ActionRow.of(testString), ActionRow.of(message))
        //                 .build();
        // event.replyModal(modal).queue();

        switch (event.getButton().getId().split(":")[1]) {
            case "captcha":
                onRequestedCaptcha(event);
                //onMessageReceived(event);
                break;
            case "twoFactor":
                event.reply(onRequestedTwoFactor(event)).queue();
                break;
            case "email":
                event.reply(onRequestedEmail(event)).queue();
                break;
            default:
                throw new IllegalStateException(event.getButton().getId());
        }
        event.getMessage().editMessageComponents(List.of()).queue();
    }

    @Nonnull
    MessageCreateData onRequestedEmail(ButtonInteractionEvent event) {
        MessageCreateBuilder builder = new MessageCreateBuilder();
        builder.addContent("Not yet implemented!");
        return builder.build();
    }

    @Nonnull
    MessageCreateData onRequestedTwoFactor(ButtonInteractionEvent event) {
        MessageCreateBuilder builder = new MessageCreateBuilder();
        builder.addContent("Not yet implemented!");
        return builder.build();
    }

    @SneakyThrows({IOException.class})
    @Nonnull
    static byte[] imageToByteArray(@Nonnull BufferedImage image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", stream);
        return Objects.requireNonNull(stream.toByteArray());
    }

    void onRequestedCaptcha(ButtonInteractionEvent event) {
        Captcha captcha = new Captcha.Builder(200, 50).addText().build();
        FileUpload file =
                FileUpload.fromData(
                        imageToByteArray(Objects.requireNonNull(captcha.getImage())), "image.png");

        event.replyFiles(file).queue();
        String answer = captcha.getAnswer();
    }
    

    // detect user input, once they hit enter, call this method
    boolean verifyCaptcha(@Nonnull String userInput, String answer){
        return userInput.equals(answer);
    }
}
