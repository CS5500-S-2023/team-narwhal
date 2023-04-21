package edu.northeastern.cs5500.starterbot.handler.button;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CaptchaButtonHandlerTest {
    private CaptchaButtonHandler captchaButtonHandler;

    @BeforeEach
    void setUp() {
        captchaButtonHandler = new CaptchaButtonHandler();
    }

    @Test
    void testGenerateCaptchaFile() throws IOException {
        /*
        Captcha captcha = new Captcha.Builder(200, 50).addText().build();
        BufferedImage image = Objects.requireNonNull(captcha.getImage());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", stream);
        FileUpload file = FileUpload.fromData(stream.toByteArray(), "image.png");
        assertThat(captchaButtonHandler.generateCaptchaFile(captcha)).isEqualTo(file);
        TODO: how to test this?
         */
    }

    @Test
    void testHandleButtonInteraction() {}
}
