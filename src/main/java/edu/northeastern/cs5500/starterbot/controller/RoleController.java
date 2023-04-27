package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;
import edu.northeastern.cs5500.starterbot.exceptions.FailedToChangeUserRoleException;
import java.util.List;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.requests.restaction.PermissionOverrideAction;
import net.dv8tion.jda.api.requests.restaction.RoleAction;

/** A class that handles role assignment flow. */
// TODO: check if it's ok to omit tests here bc of guild
@Singleton
@IgnoreInGeneratedReport // Can't test; depends on JDA Role, Guild, and etc.
public class RoleController {
    private static final String VERIFIED_ROLE_NAME = "Verified";

    @Inject
    public RoleController() {
        //
    }

    /**
     * Get an unverified role with no permissions in a guild. Creates a new one if it does not
     * exist.
     *
     * @param guild - the guild the user is trying to join
     * @return an unverified role
     */
    public Role getVerifiedRole(@Nonnull Guild guild) {
        // Loops through all roles in the guild to see if the unverified role already exists
        List<Role> roles = guild.getRolesByName(VERIFIED_ROLE_NAME, false);
        if (!roles.isEmpty()) {
            return roles.get(0);
        }

        // Creates the unverified role as it does not currently exist in the guild
        RoleAction roleAction = guild.createRole();
        roleAction.setName(VERIFIED_ROLE_NAME).setPermissions(Permission.ALL_CHANNEL_PERMISSIONS);
        Role role = roleAction.complete();
        setPermissionOverrides(guild, role);
        return role;
    }

    /**
     * Adds an unverified role to the member trying to join the guild
     *
     * @param guild - the guild the user is trying to join
     * @param user - the user trying to join the guild
     */
    public void addVerifiedRoleToUser(@Nonnull Guild guild, @Nonnull User user)
            throws FailedToChangeUserRoleException {
        try {
            Role role = getVerifiedRole(guild);

            if (role != null) {
                guild.addRoleToMember(user, role).queue();
            }
        } catch (Exception exception) {
            throw new FailedToChangeUserRoleException("Failed to add verified role to user.");
        }
    }

    /**
     * Removes a verified role from user
     *
     * @param guild - the guild the user is trying to join
     * @param user - the user trying to join the guild
     */
    public void removeVerifiedRoleFromUser(@Nonnull Guild guild, @Nonnull User user)
            throws FailedToChangeUserRoleException {
        try {
            Role role = guild.getRolesByName(VERIFIED_ROLE_NAME, false).get(0);
            if (role != null) {
                guild.removeRoleFromMember(user, role).complete();
            }
        } catch (Exception exception) {
            throw new FailedToChangeUserRoleException("Failed to remove verified role from user");
        }
    }

    /**
     * Removes all permissions from @everyone for the guild.
     *
     * @param guild - The guild we are updating permissions for.
     */
    public void updatePublicRolePermissions(@Nonnull Guild guild) {
        guild.getPublicRole()
                .getManager()
                .revokePermissions(Permission.getPermissions(Permission.ALL_GUILD_PERMISSIONS))
                .complete();
    }

    /**
     * Adds the verified role to all members in a guild.
     *
     * @param guild - The guild we are adding the verified role to all members for.
     * @throws FailedToChangeUserRoleException
     */
    public void addVerifiedRoleToAllMembers(Guild guild) throws FailedToChangeUserRoleException {
        for (Member member : guild.getMembers()) {
            User user = member.getUser();
            addVerifiedRoleToUser(guild, user);
        }
    }

    /**
     * Overrides permissions for all channels; Ensures unverified role cannot see any channels or
     * members
     *
     * @param guild - the guild the user is trying to join
     * @param role - role to give permission override to
     */
    public void setPermissionOverrides(@Nonnull Guild guild, @Nonnull Role role) {
        for (GuildChannel channel : guild.getChannels()) {
            channel.getPermissionContainer()
                    .getManager()
                    .putPermissionOverride(role, Permission.ALL_CHANNEL_PERMISSIONS, 0)
                    .complete();
        }
    }

    /**
     * Give a member of the server a certain permission in a Discord text channel.
     *
     * @param textChannel the textChannel to give permission to
     * @param member the member to give permission to
     * @param permissionToGive the type of permission to give
     * @return an PermissionOverrideAction to be queued
     */
    public PermissionOverrideAction grantUserPermissionInTextChannel(
            @Nonnull TextChannel textChannel,
            @Nonnull Member member,
            @Nonnull Permission permissionToGive) {
        PermissionOverrideAction override =
                textChannel.getPermissionContainer().upsertPermissionOverride(member);
        return override.grant(permissionToGive);
    }
}
