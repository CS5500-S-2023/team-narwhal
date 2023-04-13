package edu.northeastern.cs5500.starterbot.service;

import edu.northeastern.cs5500.starterbot.model.EventUserGuild;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import java.util.List;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.requests.restaction.PermissionOverrideAction;
import net.dv8tion.jda.api.requests.restaction.RoleAction;

/** Update user permissions. */
@Slf4j
public class UserPermissionUpdateService {
    private @Nonnull String unverifiedRoleName = "SFDB Unverified";

    @Inject GenericRepository<EventUserGuild> EventUserGuildRepository;

    @Inject
    public UserPermissionUpdateService() {}

    /**
     * Give a member of the server a certain permission in a Discord text channel.
     *
     * @param textChannel the textChannel to give permission to
     * @param member the member to give permission to
     * @param permissionToGive the type of permission to give
     * @return an PermissionOverrideAction to be queued
     */
    public PermissionOverrideAction grantUserPermissionInTextChannel(
            TextChannel textChannel, Member member, Permission permissionToGive) {
        PermissionOverrideAction override = textChannel.upsertPermissionOverride(member);
        return override.grant(permissionToGive);
    }

    // TODO: add for voice channel?

    /**
     * Return the guild id for a user using the user id from the event.
     *
     * @param eventUserID the user id from an event as a String
     * @return the guild id that the user has joined as a String
     */
    @Nonnull
    public String getGuildIdForEventUser(@Nonnull String eventUserID) {
        log.info("Updating permission for user " + eventUserID);
        for (EventUserGuild currentEventUserGuild : EventUserGuildRepository.getAll()) {
            if (eventUserID.equals(currentEventUserGuild.getUserEventId())) {
                return currentEventUserGuild.getGuildId();
            }
        }
        // throw custom user not found exceptions
        // TODO: update exception and add to method signature
        throw new RuntimeException("User not found!");
    }

    /**
     * Get an unverified role with no permissions in a guild. Creates a new one if it does not
     * exist.
     *
     * @param guild - the guild the user is trying to join
     * @return an unverified role
     */
    public Role getUnverifiedRole(@Nonnull Guild guild) {
        // Loops through all roles in the guild to see if the unverified role already exists
        List<Role> roles = guild.getRoles();
        for (Role role : roles) {
            if (role.getName().equals(unverifiedRoleName)) {
                return role;
            }
        }
        // Creates the unverified role as it does not currently exist in the guild
        RoleAction roleAction = guild.createRole();
        roleAction.setName(unverifiedRoleName).setPermissions(Permission.EMPTY_PERMISSIONS);
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
    public void addUnverifiedRoleToUser(@Nonnull Guild guild, @Nonnull User user) {
        try {
            Role role = getUnverifiedRole(guild);

            if (role != null) {
                guild.addRoleToMember(user, role).queue();
            }
        } catch (Exception exception) {
            log.info("Can't add unverified role to user");
        }
    }

    /**
     * Overrides permissions for all channels Ensures unverified role cannot see any channels or
     * members
     *
     * @param guild - the guild the user is trying to join
     * @param role - role to give permission override to
     */
    public void setPermissionOverrides(@Nonnull Guild guild, Role role) {
        for (GuildChannel channel : guild.getChannels()) {
            channel.getPermissionContainer().upsertPermissionOverride(role).complete();
        }
    }

    /**
     * Removes an unverified role to the member trying to join the guild
     *
     * @param guild - the guild the user is trying to join
     * @param user - the user trying to join the guild
     */
    public void removeUnverifiedRoleFromUser(@Nonnull Guild guild, @Nonnull User user) {
        try {
            Role role = guild.getRolesByName(unverifiedRoleName, false).get(0);
            if (role != null) {
                guild.removeRoleFromMember(user, role).complete();
            }
        } catch (Exception exception) {
            log.info("Can't remove unverified role for user");
        }
    }
}
