package edu.northeastern.cs5500.starterbot.handler.slash;

import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;
import edu.northeastern.cs5500.starterbot.config.command.VerifyAllSlashCommand;
import edu.northeastern.cs5500.starterbot.controller.GuildController;
import edu.northeastern.cs5500.starterbot.controller.RoleController;
import edu.northeastern.cs5500.starterbot.exceptions.FailedToChangeUserRoleException;
import javax.inject.Inject;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import javax.annotation.Nonnull;

/** Handles the /verify-all command. */
public class VerifyAllSlashCommandHandler implements SlashCommandHandler {
    VerifyAllSlashCommand config;
    GuildController guildController;
    RoleController roleController;

    @Inject
    VerifyAllSlashCommandHandler(
            VerifyAllSlashCommand config,
            GuildController guildController,
            RoleController roleController) {
        this.config = config;
        this.guildController = guildController;
        this.roleController = roleController;
    }

    /**
     * Gets the name from the config object.
     *
     * @return the name from the config object.
     */
    @Nonnull
    public String getName() {
        return config.getName();
    }

    /** Method that runs after the /verify-all command is used. */
    @IgnoreInGeneratedReport // Can't test; depends on JDA
    public void handleSlashCommandInteraction(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        Guild guild = event.getGuild();

        if (guild != null) {
            try {
                roleController.addVerifiedRoleToAllMembers(guild);
                event.getHook()
                        .sendMessage("All members now have the \"Verified\" role.")
                        .setEphemeral(true)
                        .queue();
            } catch (FailedToChangeUserRoleException e) {
                event.getHook()
                        .sendMessage("Failed to add verified role to all members.")
                        .setEphemeral(true)
                        .queue();
            }
        }
    }
}
