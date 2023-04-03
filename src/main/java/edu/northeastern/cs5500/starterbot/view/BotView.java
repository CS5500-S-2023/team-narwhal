
package edu.northeastern.cs5500.starterbot.view;
import edu.northeastern.cs5500.starterbot.authentication.AuthenticationHandler;
import java.util.*;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

@Singleton
public class BotView {
    private Map<AuthenticarionType, AuthenticationHandler> authenticationMethods;

    @Inject
    UserEnterView(Map<AuthenticarionType, AuthenticationHandler> authenticationMethods){
        this.authenticationMethods = authenticationMethods;
    }

    public FileUpload generateCaptchaView(Captcha captcha) {
        return FileUpload.fromData(
                        imageToByteArray(Objects.requireNonNull(captcha.getImage())), "image.png");
        }

    public MessageCreateBuilder generateWelcomeMsg() {
    // Creating message string to send user in private channel
    String welcomeMessage =
    String.format(
            "Welcome to %s! Before you can get access to the server, please verify using one of the following methods:",
            event.getGuild());

    // Builds the message and adds buttons
    MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();

    Set<Button> buttons = new HashSet<>();
    for (AuthenticationHandler auth : authenticationMethods.values()) {
        buttons.add(createButton(auth));
    }
    return messageCreateBuilder.addActionRow(buttons).setContent(welcomeMessage);
    }

    private Button createButton(AuthenticationHandler authenticationHandler){
        return Button.primary(authenticationHandler.getName() + ":" + authenticationHandler.getId(), authenticationHandler.getLabel());
    }
}