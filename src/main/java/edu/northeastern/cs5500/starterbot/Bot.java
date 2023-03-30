package edu.northeastern.cs5500.starterbot;

import dagger.Component;
import edu.northeastern.cs5500.starterbot.authentication.AuthenticationModule;
import edu.northeastern.cs5500.starterbot.command.CommandModule;
import edu.northeastern.cs5500.starterbot.listener.ButtonListener;
import edu.northeastern.cs5500.starterbot.listener.AuthenticationListener;
import edu.northeastern.cs5500.starterbot.listener.MessageListener;
import edu.northeastern.cs5500.starterbot.repository.RepositoryModule;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

@Component(modules = {CommandModule.class, RepositoryModule.class, AuthenticationModule.class})
@Singleton
interface BotComponent {
    public Bot bot();
}

public class Bot {

    @Inject
    Bot() {}

    @Inject MessageListener messageListener;
    @Inject AuthenticationListener eventListener;
    @Inject ButtonListener buttonListener;

    static String getBotToken() {
        return new ProcessBuilder().environment().get("BOT_TOKEN");
    }

    void start() {
        String token = getBotToken();
        if (token == null) {
            throw new IllegalArgumentException(
                    "The BOT_TOKEN environment variable is not defined.");
        }
        @SuppressWarnings("null")
        @Nonnull
        JDA jda =
                JDABuilder.createDefault(
                                token,
                                GatewayIntent.GUILD_MEMBERS,
                                GatewayIntent.GUILD_MESSAGES,
                                GatewayIntent.DIRECT_MESSAGES,
                                GatewayIntent.GUILD_MESSAGE_REACTIONS)
                        .setDisabledIntents(
                                GatewayIntent.GUILD_VOICE_STATES,
                                GatewayIntent.GUILD_EMOJIS_AND_STICKERS)
                        .addEventListeners(messageListener, eventListener, buttonListener)
                        .build();

        CommandListUpdateAction commands = jda.updateCommands();
        commands.addCommands(messageListener.allCommandData());
        commands.queue();
    }
}
