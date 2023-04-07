package edu.northeastern.cs5500.starterbot;

import dagger.Component;
import edu.northeastern.cs5500.starterbot.config.authentication.AuthenticationModule;
import edu.northeastern.cs5500.starterbot.config.command.CommandModule;
import edu.northeastern.cs5500.starterbot.config.command.SlashCommandConfig;
import edu.northeastern.cs5500.starterbot.listener.AuthenticationListener;
import edu.northeastern.cs5500.starterbot.repository.RepositoryModule;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
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

    @Inject
    Set<SlashCommandConfig> commands;

    @Inject AuthenticationListener authenticationListener;

    static String getBotToken() {
        return new ProcessBuilder().environment().get("BOT_TOKEN");
    }

    // get all the commands supported by the program
    private @Nonnull Collection<CommandData> allCommandData() {
        // you can also use a for loop, add everything from the set
        Collection<CommandData> commandData =
            commands.stream()
                .map(SlashCommandConfig::getCommandData)
                .collect(Collectors.toList());
        if (commandData == null) {
            return new ArrayList<>();
        }
        return commandData;
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
                .addEventListeners(authenticationListener)
                .build();

        CommandListUpdateAction commands = jda.updateCommands();
        // right now we only have slash commands
        commands.addCommands(allCommandData());
        commands.queue();
        //jda.awaitReady(); is this needed?
    }
}
