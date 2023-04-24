package edu.northeastern.cs5500.starterbot.handler.slash;

import edu.northeastern.cs5500.starterbot.config.command.SetupSlashCommand;
import edu.northeastern.cs5500.starterbot.controller.GuildController;
import edu.northeastern.cs5500.starterbot.controller.RoleController;
import edu.northeastern.cs5500.starterbot.exceptions.FailedToChangeUserRoleException;
import javax.inject.Inject;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/** A class to handle /setup command. */
public class SetupSlashCommandHandler implements SlashCommandHandler {
    SetupSlashCommand config;
    GuildController guildController;
    RoleController roleController;

    @Inject
    SetupSlashCommandHandler(
            SetupSlashCommand config,
            GuildController guildController,
            RoleController roleController) {
        this.config = config;
        this.guildController = guildController;
        this.roleController = roleController;
    }

    /**
     * Gets the name of the slash command config.
     *
     * @return - The name of the slash command config.
     */
    public String getName() {
        return config.getName();
    }

    /** Runs after a /setup command is entered. */
    public void handleSlashCommandInteraction(SlashCommandInteractionEvent event) {
        event.deferReply().setEphemeral(true).queue();
        Guild guild = event.getGuild();

        if (guild != null) {
            String guildId = guild.getId();
            guildController.startGuildSetup(guildId);

            try {
                String statusMsg = "Server setup status:";

                roleController.updatePublicRolePermissions(guild);
                statusMsg = getPublicRolePermissionsUpdatedMsg(statusMsg);
                event.getHook().sendMessage(statusMsg).queue();

                var option = event.getOption(config.getOptionName());
                boolean verifyAll = option != null && option.getAsBoolean();
                if (verifyAll) {
                    roleController.addVerifiedRoleToAllMembers(guild);
                    statusMsg = getAllMemberVerifiedMsg(statusMsg);
                    event.getHook().sendMessage(statusMsg).queue();
                }

                guildController.onGuildSetupComplete(guildId);
            } catch (FailedToChangeUserRoleException e) {
                event.getHook()
                        .sendMessage("Failed to add verified role to all members.")
                        .setEphemeral(true)
                        .queue();
            }
        }
    }

    /**
     * Concatenates the server permissions updated message to message we are going to send the
     * member after the guild setup is complete.
     *
     * @param msg - The message to concatentate to.
     * @return A message to send the user after the guild setup is complete.
     */
    public String getPublicRolePermissionsUpdatedMsg(String msg) {
        return String.format("%s%n%s", msg, "✅ Server permissions updated.");
    }

    /**
     * Concatenates the verified role has been given to all members message to message we are going
     * to send the member after the guild setup is complete.
     *
     * @param msg - The message to concatentate to.
     * @return A message to send the user after the guild setup is complete.
     */
    public String getAllMemberVerifiedMsg(String msg) {
        return String.format("%s%n%s", msg, "✅ \"Verified\" role has been given to all members.");
    }
}
