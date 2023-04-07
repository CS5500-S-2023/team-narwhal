package edu.northeastern.cs5500.starterbot;

import dagger.Component;
import edu.northeastern.cs5500.starterbot.config.authentication.AuthenticationModule;
import edu.northeastern.cs5500.starterbot.config.command.CommandModule;
import edu.northeastern.cs5500.starterbot.listener.AuthenticationListener;
import edu.northeastern.cs5500.starterbot.listener.MessageListener;
import edu.northeastern.cs5500.starterbot.repository.RepositoryModule;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

@Component(modules = {CommandModule.class, RepositoryModule.class, AuthenticationModule.class})
@Singleton
interface BotComponent {
    Bot bot();
}

public class Bot {

    @Inject
    Bot() {}

    @Inject MessageListener messageListener;
    @Inject AuthenticationListener authenticationListener;

    static String getBotToken() {
        return new ProcessBuilder().environment().get("BOT_TOKEN");
    }

    void start() throws InterruptedException {
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
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(messageListener, authenticationListener)
                .build();
        CommandListUpdateAction commands = jda.updateCommands();
        commands.addCommands(messageListener.allCommandData());
        commands.queue();
        //jda.awaitReady(); is this needed?
    }
}
