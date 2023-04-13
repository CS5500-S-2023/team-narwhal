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
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import nl.captcha.Captcha;

/**
 * Gets data from controller and builds different view objects that the program presents to the
 * user, e.g., buttons, messages, images, etc.
 */
@Singleton
public class BotView {

    private final Map<AuthenticationType, AuthenticationConfig> authenticationMethods;

    // constructor
    @Inject
    public BotView(Map<AuthenticationType, AuthenticationConfig> authenticationMethods) {
        this.authenticationMethods = authenticationMethods;
    }

    /**
     * Builds a welcome message with buttons, used by UserEnterController.
     *
     * @param guildName the guild name to be used in the welcome message
     * @return an MessageCreateBuilder with a welcome msg and buttons
     */
    public MessageCreateBuilder generateWelcomeMsg(String guildName) {
        // Creating message string to send user in private channel
        String welcomeMessage =
                String.format(
                        "Welcome to %s! Before you can get access to the server, please verify using one of "
                                + "the following methods:",
                        guildName);

        // Builds the message and adds buttons
        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();

        Set<Button> buttons = new HashSet<>();
        for (AuthenticationConfig auth : authenticationMethods.values()) {
            // calls helper to create the buttons
            buttons.add(createButton(auth));
        }
        return messageCreateBuilder.addActionRow(buttons).setContent(welcomeMessage);
    }

    /**
     * Private helper to build the buttons.
     *
     * @param authenticationConfig an AuthenticationConfig (authentication configuration type) that
     *     the program supports
     * @return a custom button for this configuration type
     */
    private static Button createButton(AuthenticationConfig authenticationConfig) {
        return Button.primary(
                authenticationConfig.getName() + ":" + authenticationConfig.getId(),
                authenticationConfig.getLabel());
    }

    /**
     * Creates a captcha file that can be uploaded and rendered to the user, used by
     * ButtonClickController.
     *
     * @param captcha the Captcha object to be rendered
     * @return a FileUpload object that can be rendered
     */
    public static FileUpload generateCaptchaView(Captcha captcha) {
        return FileUpload.fromData(
                imageToByteArray(Objects.requireNonNull(captcha.getImage())), "image.png");
    }

    /**
     * Private helper to convert a BufferedImage object to byte array.
     *
     * @param image the BufferedImage to be converted
     * @return the byte array representation of the image
     */
    @SneakyThrows({IOException.class})
    @Nonnull
    private static byte[] imageToByteArray(@Nonnull BufferedImage image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", stream);
        return Objects.requireNonNull(stream.toByteArray());
    }
}
