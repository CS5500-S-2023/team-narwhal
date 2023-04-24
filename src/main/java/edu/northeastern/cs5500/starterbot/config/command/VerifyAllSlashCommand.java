package edu.northeastern.cs5500.starterbot.config.command;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/** Configuration class for a /verify-all command. */
@Singleton
public class VerifyAllSlashCommand implements SlashCommandConfig {
    public static final String NAME = "verify-all";

    @Inject
    public VerifyAllSlashCommand() {
        //
    }

    /**
     * Gets the name of the slash command.
     *
     * @return the name of the slash command.
     */
    @Override
    @Nonnull
    public String getName() {
        return NAME;
    }

    /**
     * Gets the CommandData for a /verify-all slash command.
     *
     * @return a CommandData.
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Add verified role to all current members of servers.")
                .setDefaultPermissions(
                        DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }
}
