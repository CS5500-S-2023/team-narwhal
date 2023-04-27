package edu.northeastern.cs5500.starterbot.handler.message;

import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;
import edu.northeastern.cs5500.starterbot.config.authentication.AuthenticationConfig;
import edu.northeastern.cs5500.starterbot.config.authentication.AuthenticationType;
import edu.northeastern.cs5500.starterbot.controller.AuthenticationController;
import edu.northeastern.cs5500.starterbot.exceptions.FailedToSendMessageException;
import edu.northeastern.cs5500.starterbot.exceptions.NoAuthenticationSessionException;
import edu.northeastern.cs5500.starterbot.exceptions.UnknownAuthenticationStateException;
import edu.northeastern.cs5500.starterbot.model.AuthenticationChallenge;
import edu.northeastern.cs5500.starterbot.model.AuthenticationState;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

/** A class to handle messages related to authentication. */
@IgnoreInGeneratedReport // Can't test; depends on JDA
@Slf4j
public class AuthenticateMessageHandler implements MessageHandler {
    @Inject Map<AuthenticationType, AuthenticationConfig> authenticationMethods;

    AuthenticationController authenticationController;

    @Inject
    public AuthenticateMessageHandler(AuthenticationController authenticationController) {
        this.authenticationController = authenticationController;
    }

    /**
     * Handles message received event. Ignore message if there's no authentication session for
     * message author
     *
     * @param event - The message received event.
     */
    @SneakyThrows
    public void handleMessage(@Nonnull MessageReceivedEvent event) {
        String userId = event.getAuthor().getId();
        String userInput = event.getMessage().getContentRaw();
        try {
            AuthenticationChallenge challenge =
                    authenticationController.attemptChallenge(userId, userInput);
            sendChallengeResultMessage(event, challenge.getState());
        } catch (NoAuthenticationSessionException e) {
            log.error(String.format("No authentication session for %s", userId), e);
        } catch (FailedToSendMessageException e) {
            // future implementation: wait for x amount of time to retry sending message
            log.error("Failed to send message", e);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Sends a message depending on the AuthenticationState in the AuthenticationChallenge.
     *
     * @param event - The message received event.
     * @param state - The AuthenticationState of the AuthenticationChallenge.
     * @throws FailedToSendMessageException
     * @throws UnknownAuthenticationStateException
     */
    public void sendChallengeResultMessage(MessageReceivedEvent event, AuthenticationState state)
            throws FailedToSendMessageException, UnknownAuthenticationStateException {
        switch (state) {
            case WAITING_FOR_RESPONSE:
                sendWaitingForResponseMessage(event);
                return;
            case VERIFIED:
                sendSuccessMessage(event);
                return;
            case INCORRECT_RESPONSE:
                sendRetryMessage(event);
                return;
            case TOO_MANY_ATTEMPTS:
                sendLockedOutMessage(event);
                return;
            case FAILED_TO_SEND:
                throw new FailedToSendMessageException("Failed to send message");
            case UNKNOWN:
            default:
                throw new UnknownAuthenticationStateException("Challenge state unknown");
        }
    }

    /**
     * Sends a message when the AuthenticationState is set to VERIFIED.
     *
     * @param event - The message received event.
     */
    public void sendSuccessMessage(MessageReceivedEvent event) {
        MessageCreateBuilder msg = new MessageCreateBuilder().setContent("You are verified!");
        event.getAuthor().openPrivateChannel().complete().sendMessage(msg.build()).queue();
    }

    /**
     * Sends a message when the AuthenticationState is set to INCORRECT_RESPONSE.
     *
     * @param event - The message received event.
     */
    public void sendRetryMessage(MessageReceivedEvent event) {
        User user = event.getAuthor();
        MessageCreateBuilder msg = generateRetryMsg();
        user.openPrivateChannel().complete().sendMessage(msg.build()).queue();
    }
    /**
     * Not yet implemented. For now, allow infinite retries. Future implementation: send message
     * indicating user is locked out for n amount of time and cannot retry authentication before
     * then.
     */
    public void sendLockedOutMessage(MessageReceivedEvent event) {
        sendRetryMessage(event);
    }

    /**
     * Generates a message when the AuthenticationState is set to INCORRECT_RESPONSE.
     *
     * @return - A message with the authentication method buttons.
     */
    public MessageCreateBuilder generateRetryMsg() {
        String msg = "That is incorrect. Please verify using one of the following methods:";
        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();
        List<Button> buttons =
                authenticationMethods.values().stream()
                        .map(AuthenticationConfig::asButton)
                        .sorted(Comparator.comparing(Button::getLabel))
                        .collect(Collectors.toList());

        return messageCreateBuilder.addActionRow(Objects.requireNonNull(buttons)).setContent(msg);
    }

    /**
     * Sends a message when the AuthenticationState is set to WAITING_FOR_RESPONSE.
     *
     * @param event - The message received event.
     */
    public void sendWaitingForResponseMessage(MessageReceivedEvent event) {
        MessageCreateBuilder msg = new MessageCreateBuilder().setContent("Waiting for response...");
        event.getAuthor().openPrivateChannel().complete().sendMessage(msg.build()).queue();
    }
}
