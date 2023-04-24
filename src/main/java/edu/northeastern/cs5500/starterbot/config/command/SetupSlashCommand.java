package edu.northeastern.cs5500.starterbot.config.command;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/** Configuration class for a /setup command. */
@Singleton
public class SetupSlashCommand implements SlashCommandConfig {
    public static final String NAME = "setup";
    public static final String VERIFY_ALL_OPTION = "verify-all";

    @Inject
    public SetupSlashCommand() {
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
     * Gets the name of the option for the slash command.
     *
     * @return the name of the option for the slash command.
     */
    @Nonnull
    public String getOptionName() {
        return VERIFY_ALL_OPTION;
    }

    /**
     * Gets the CommandData for a /setup slash command.
     *
     * @return a CommandData.
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(
                        getName(),
                        "Setup server: update @everyone permissions; optionally auto-verify all current members.")
                .addOption(
                        OptionType.BOOLEAN,
                        VERIFY_ALL_OPTION,
                        "Add \"Verified\" role to all current members.")
                .setDefaultPermissions(
                        DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }
}
