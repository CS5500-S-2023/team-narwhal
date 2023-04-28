package edu.northeastern.cs5500.starterbot;

import dagger.Component;
import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;
import edu.northeastern.cs5500.starterbot.config.authentication.AuthenticationModule;
import edu.northeastern.cs5500.starterbot.config.command.CommandModule;
import edu.northeastern.cs5500.starterbot.config.command.SlashCommandConfig;
import edu.northeastern.cs5500.starterbot.handler.button.ButtonModule;
import edu.northeastern.cs5500.starterbot.handler.join.JoinModule;
import edu.northeastern.cs5500.starterbot.handler.message.MessageModule;
import edu.northeastern.cs5500.starterbot.handler.slash.SlashCommandModule;
import edu.northeastern.cs5500.starterbot.listener.ButtonListener;
import edu.northeastern.cs5500.starterbot.listener.GuildMemberJoinListener;
import edu.northeastern.cs5500.starterbot.listener.MessageListener;
import edu.northeastern.cs5500.starterbot.listener.SlashCommandListener;
import edu.northeastern.cs5500.starterbot.repository.RepositoryModule;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

@Component(
        modules = {
            RepositoryModule.class,
            AuthenticationModule.class,
            ButtonModule.class,
            JoinModule.class,
            MessageModule.class,
            CommandModule.class,
            SlashCommandModule.class,
        })
@IgnoreInGeneratedReport
@Singleton
interface BotComponent {
    public Bot bot();
}

/** Represents a discord bot instance with the supported functionalities, such as button clicks. */
@IgnoreInGeneratedReport
public class Bot {
    ButtonListener buttonListener;
    GuildMemberJoinListener guildMemberJoinListener;
    MessageListener messageListener;
    SlashCommandListener slashCommandListener;

    @Inject Map<String, Provider<SlashCommandConfig>> commandConfigs;

    @Inject
    Bot(
            ButtonListener buttonListener,
            GuildMemberJoinListener guildMemberJoinListener,
            MessageListener messageListener,
            SlashCommandListener slashCommandListener) {
        this.buttonListener = buttonListener;
        this.guildMemberJoinListener = guildMemberJoinListener;
        this.messageListener = messageListener;
        this.slashCommandListener = slashCommandListener;
    }

    /***
     * Get the bot token from the program environment.
     * @return a bot token represented as a String
     */
    static String getBotToken() {
        return new ProcessBuilder().environment().get("BOT_TOKEN");
    }

    /**
     * Get all the commands supported by the program.
     *
     * @return all commands data objects stored in a collection
     */
    private @Nonnull Collection<CommandData> allCommandData() {
        // you can also use a for loop, add everything from the set
        Collection<CommandData> commandData =
                commandConfigs.values().stream()
                        .map(Provider::get)
                        .map(SlashCommandConfig::getCommandData)
                        .collect(Collectors.toList());
        if (commandData == null) {
            return new ArrayList<>();
        }
        return commandData;
    }

    /**
     * Starts a jda bot session.
     *
     * @throws InterruptedException if the session is interrupted.
     */
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
                        .addEventListeners(
                                buttonListener,
                                guildMemberJoinListener,
                                messageListener,
                                slashCommandListener)
                        .build();

        CommandListUpdateAction commands = jda.updateCommands();
        commands.addCommands(allCommandData());
        commands.queue();
    }
}
