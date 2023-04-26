package edu.northeastern.cs5500.starterbot.config.authentication;

import javax.annotation.Nonnull;
import lombok.Data;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

/** Configuration class for an authentication method. */
@Data
public abstract class AuthenticationConfig {
    public static final String NAME = "authenticate";
    @Nonnull public String id;
    @Nonnull public String label;

    /**
     * Generates a button for the authentication method.
     *
     * @return a Button.
     */
    public Button asButton() {
        return Button.primary(String.format("%s:%s", NAME, id), getLabel());
    }
}
