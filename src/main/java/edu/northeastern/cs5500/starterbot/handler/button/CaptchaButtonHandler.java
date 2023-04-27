package edu.northeastern.cs5500.starterbot.handler.button;

import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import net.dv8tion.jda.api.utils.messages.MessageEditData;
import nl.captcha.Captcha;

/** A class to handle when a CAPTCHA button is clicked. */
public class CaptchaButtonHandler extends AuthenticationButtonHandler {

    @Inject
    public CaptchaButtonHandler() {
        super("captcha");
    }

    /** Runs after a CAPTCHA button is clicked. */
    @IgnoreInGeneratedReport // can't test; depends on JDA and SimpleCaptcha
    @Override
    public void handleButtonInteraction(ButtonInteractionEvent event) {
        // Edit message to accomplish 2 things:
        //  1. to acknowledge it within the allowed limit
        //  2. delete authentication buttons so user cannot trigger it again
        MessageCreateData reply = generateLoadingCaptchaMsg();
        event.editMessage(MessageEditData.fromCreateData(reply)).queue();

        String userId = event.getUser().getId();
        Captcha captcha = new Captcha.Builder(200, 50).addText().build();
        authenticationController.startChallenge(userId, captcha.getAnswer());

        FileUpload captchaFile = generateCaptchaFile(captcha);
        MessageCreateData msg =
                new MessageCreateBuilder()
                        .setContent("Please reply with the code displayed below:")
                        .addFiles(captchaFile)
                        .build();
        event.getChannel().sendMessage(msg).queue();
    }

    /**
     * Creates a captcha file that can be uploaded and rendered to the user, used by
     * ButtonClickController.
     *
     * @return a FileUpload object that can be rendered
     */
    @IgnoreInGeneratedReport // can't test; depends on JDA
    public FileUpload generateCaptchaFile(Captcha captcha) {
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

    @IgnoreInGeneratedReport // can't test; depends on JDA
    @Nonnull
    private MessageCreateData generateLoadingCaptchaMsg() {
        return new MessageCreateBuilder().setContent("Generating captcha...").build();
    }
}
