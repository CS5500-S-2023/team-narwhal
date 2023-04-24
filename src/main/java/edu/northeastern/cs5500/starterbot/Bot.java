package edu.northeastern.cs5500.starterbot;

import dagger.Component;
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
import edu.northeastern.cs5500.starterbot.service.ServiceModule;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

@Component(
        modules = {
            RepositoryModule.class,
            AuthenticationModule.class,
            ButtonModule.class,
            JoinModule.class,
            MessageModule.class,
            CommandModule.class,
            SlashCommandModule.class,
            ServiceModule.class,
        })
@Singleton
interface BotComponent {
    public Bot bot();
}

@Slf4j
public class Bot {
    ButtonListener buttonListener;
    GuildMemberJoinListener guildMemberJoinListener;
    MessageListener messageListener;
    SlashCommandListener slashCommandListener;

    @Inject JDA jda;
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

    // get all the commands supported by the program
    private @Nonnull Collection<CommandData> allCommandData() {
        // you can also use a for loop, add everything from the set
        Collection<CommandData> commandData =
                commandConfigs.entrySet().stream()
                        .map(provider -> provider.getValue().get())
                        .map(SlashCommandConfig::getCommandData)
                        .collect(Collectors.toList());
        if (commandData == null) {
            return new ArrayList<>();
        }
        return commandData;
    }

    void start() throws InterruptedException {
        try {
            jda.addEventListener(
                    buttonListener, guildMemberJoinListener, messageListener, slashCommandListener);

            CommandListUpdateAction commands = jda.updateCommands();
            // right now we only have slash commands
            commands.addCommands(allCommandData());
            commands.queue();
        } catch (Exception e) {
            log.error("Unable to add message listeners", e);
        }
    }
}
