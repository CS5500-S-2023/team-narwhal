package edu.northeastern.cs5500.starterbot.config.authentication;

import javax.annotation.Nonnull;
import lombok.Data;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

@Data
public abstract class AuthenticationConfig {

    @Nonnull public String id;
    @Nonnull public final String name = "authenticate"; // button type
    @Nonnull public String label;

    protected AuthenticationConfig(@Nonnull String id, @Nonnull String label) {
        this.id = id;
        this.label = label;
    }

    public Button asButton() {
        return Button.primary(String.format("%s:%s", getName(), getId()), getLabel());
    }
}
