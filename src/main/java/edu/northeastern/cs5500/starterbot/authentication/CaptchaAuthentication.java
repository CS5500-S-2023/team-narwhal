package edu.northeastern.cs5500.starterbot.authentication;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.entities.User;
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
public class CaptchaAuthentication extends Authentication {
    private String userInput;
    private String answer;
    private boolean isVerified;

    @Inject
    public CaptchaAuthentication() {
        super("captcha", "CAPTCHA");
    }
    
    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        onRequestedCaptcha(event);
    }
    void onRequestedCaptcha(ButtonInteractionEvent event) {
        Captcha captcha = new Captcha.Builder(200, 50).addText().build();
        FileUpload file =
                FileUpload.fromData(
                        imageToByteArray(Objects.requireNonNull(captcha.getImage())), "image.png");

        event.replyFiles(file).queue();
        this.answer = captcha.getAnswer();
    }

    @SneakyThrows({IOException.class})
    @Nonnull
    static byte[] imageToByteArray(@Nonnull BufferedImage image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", stream);
        return Objects.requireNonNull(stream.toByteArray());
    }
}
