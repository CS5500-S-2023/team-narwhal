package edu.northeastern.cs5500.starterbot.view;

import edu.northeastern.cs5500.starterbot.config.authentication.AuthenticationConfig;
import edu.northeastern.cs5500.starterbot.config.authentication.AuthenticationType;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import nl.captcha.Captcha;

@Singleton
public class BotView {
    private Map<AuthenticationType, AuthenticationConfig> authenticationMethods;

    // constructor
    @Inject
    public BotView(Map<AuthenticationType, AuthenticationConfig> authenticationMethods) {
        this.authenticationMethods = authenticationMethods;
    }

    public MessageCreateBuilder generateWelcomeMsg(@Nonnull GuildMemberJoinEvent event) {
        // Creating message string to send user in private channel
        String welcomeMessage =
                String.format(
                        "Welcome to %s! Before you can get access to the server, please verify using one of the following methods:",
                        event.getUser().getId());

        // Builds the message and adds buttons
        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();

        Set<Button> buttons = new HashSet<>();
        for (AuthenticationConfig auth : authenticationMethods.values()) {
            buttons.add(createButton(auth));
        }
        return messageCreateBuilder.addActionRow(buttons).setContent(welcomeMessage);
    }

    private Button createButton(AuthenticationConfig authenticationConfig) {
        return Button.primary(
            authenticationConfig.getName() + ":" + authenticationConfig.getId(),
            authenticationConfig.getLabel());
    }

    public FileUpload generateCaptchaView(Captcha captcha) {
        return FileUpload.fromData(
                imageToByteArray(Objects.requireNonNull(captcha.getImage())), "image.png");
    }

    @SneakyThrows({IOException.class})
    @Nonnull
    private static byte[] imageToByteArray(@Nonnull BufferedImage image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", stream);
        return Objects.requireNonNull(stream.toByteArray());
    }
}
